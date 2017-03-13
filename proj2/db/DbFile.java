package db;


import java.util.*;
import java.io.*;

/**
 * The interface for database files on disk. Each table is represented by a
 * single DbFile. DbFiles can fetch pages and iterate through rows. Each
 * file has a unique id used to store metadata about the table in the Catalog.
 * DbFiles are generally accessed through the buffer pool, rather than directly
 * by operators.
 */
public interface DbFile extends Serializable {
    /**
     * Read the specified page from disk.
     *
     * @throws IllegalArgumentException if the page does not exist in this file.
     */
    public Page readPage(PageId id);

    /**
     * Push the specified page to disk.
     *
     * @param p The page to write.  page.getId().pageno() specifies the offset into the file where the page should be written.
     * @throws IOException if the write fails
     *
     */
    public void writePage(Page p) throws IOException;

    /**
     * Inserts the specified Row to the file on behalf of transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.
     *
     * @param tid The transaction performing the update
     * @param r The Row to add.  This Row should be updated to reflect that
     *          it is now stored in this file.
     * @return An ArrayList contain the pages that were modified
     * @throws DbException if the Row cannot be added
     * @throws IOException if the needed file can't be read/written
     */
    public ArrayList<Page> insertRow(TransactionId tid, Row r)
            throws DbException, IOException, TransactionAbortedException;

    /**
     * Removes the specifed Row from the file on behalf of the specified
     * transaction.
     * This method will acquire a lock on the affected pages of the file, and
     * may block until the lock can be acquired.
     *
     * @throws DbException if the Row cannot be deleted or is not a member
     *   of the file
     */
    public Page deleteRow(TransactionId tid, Row r)
            throws DbException, TransactionAbortedException;

    /**
     * Returns an iterator over all the Rows stored in this DbFile. The
     * iterator must use {@link BufferPool#getPage}, rather than
     * {@link #readPage} to iterate through the pages.
     *
     * @return an iterator over all the Rows stored in this DbFile.
     */
    public DbFileIterator iterator(TransactionId tid);

    /**
     * Returns a unique ID used to identify this DbFile in the Catalog. This id
     * can be used to look up the table via {@link Catalog#getDbFile} and
     * {@link Catalog#getRowDesc}.
     * <p>
     * Implementation note:  you will need to generate this tableid somewhere,
     * ensure that each HeapFile has a "unique id," and that you always
     * return the same value for a particular HeapFile. A simple implementation
     * is to use the hash code of the absolute path of the file underlying
     * the HeapFile, i.e. <code>f.getAbsoluteFile().hashCode()</code>.
     *
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId();

    /**
     * Returns the RowDesc of the table stored in this DbFile.
     * @return RowDesc of this DbFile.
     */
    public RowDesc getRowDesc();
}
