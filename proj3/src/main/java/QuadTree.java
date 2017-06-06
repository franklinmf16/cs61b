import java.util.ArrayList;

/**
 * Created by GritShiva on 2017/5/30 0030.
 */
public class QuadTree{




        public Node root;

        public QuadTree(Node root) {
            this.root = root;
        }

        public void insert(double ulX, double lrX, double ulY, double lrY, String imgFile){
            root = insert(root,ulX,lrX,ulY,lrY,imgFile);
        }


        private Node insert(Node h,double ulX, double lrX, double ulY, double lrY, String imgFile){

            int num = Integer.parseInt(imgFile);
            int quadrant = num;
            while( quadrant >= 10){
                quadrant /= 10;
            }

            //String substring(int beginIndex): Returns the substring starting from the specified
            // index(beginIndex) till the end of the string.
            // For e.g. "Chaitanya".substring(2) would return "aitanya".

            if(num >= 10){
                num = Integer.parseInt(imgFile.substring(1));
            }


            if(h == null || num <= 0){
                return new Node(ulX,lrX,ulY,lrY,imgFile);
            }else if(quadrant == 1){
                h.UL = insert(h.UL,ulX,(ulX+lrX)/2,ulY,(ulY+lrY)/2,imgFile);
            }else if(quadrant == 2){
                h.UR = insert(h.UR,(ulX+lrX)/2,lrX,ulY,(ulY+lrY)/2,imgFile);
            }else if(quadrant == 3){
                h.LL = insert(h.LL,ulX,(ulX+lrX)/2,(ulY+lrY)/2,lrY,imgFile);
            }else if(quadrant == 4){
                h.LR = insert(h.LR,(ulX+lrX)/2,lrX,(ulY+lrY)/2,lrY,imgFile);
            }

            return h;

        }

        public ArrayList<Node> getNodes(){


        }

        public ArrayList<Node> helpGetNodes(){


        }

        public String[][] getImages(){


        }


        public QueryBox getQB(){

        }



        public static class Node implements Comparable<Node> {

            double upLeftLongitude;
            double lowerRightLongitude;
            double upLeftLatitude;
            double lowerRightLatitude;
            String imgFile;
            Node UL, UR, LL, LR;

            Node(double ulX, double lrX, double ulY, double lrY, String imgFile) {
                this.upLeftLongitude = ulX;
                this.upLeftLatitude = ulY;
                this.lowerRightLongitude = lrX;
                this.lowerRightLatitude = lrY;
                this.imgFile = imgFile;
            }

            double getLonDPP(double ulX, double lrX, double ulY, double lrY) {
                return Rasterer.degreeofLongitude * Math.abs(lrX - ulX) / MapServer.TILE_SIZE;
            }


            @Override
            public int compareTo(Node o) {
                if (this.upLeftLatitude == o.upLeftLatitude) {
                    if(this.upLeftLongitude < o.upLeftLongitude){
                        return  -1;
                    }else {
                        return 1;
                    }
                }else{
                    if(this.upLeftLatitude > o.upLeftLatitude){
                        return -1;
                    }else{
                        return  1;
                    }
                }
            }

            public String getImgFile(){
                return this.imgFile;
            }

        }



        public boolean intersectsQueryBox(double query_ulX, double query_ulY, double query_lrX, double query_lrY){

            if( root.upLeftLongitude > query_lrX || root.upLeftLatitude < query_lrY ||
                    root.lowerRightLongitude < query_ulX || root.lowerRightLatitude > query_ulY){
                return false;
            } else {
                return true;
            }
        }


    /** What's a quadtree intersection query?
     * Think about a range query on a binary search tree.
     * Given some binary search tree on integers, I want you to return me all integers of depth 4 in between 8 and 69 -
     * how do you do that? Now, if you can do that, can you do that in two dimensions? Instead of integers,
     * now we have squares that span certain ranges. There are many approaches to solving this.

     */



    }


