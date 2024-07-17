package dsaa.lab01;

public class Drawer {
	//private method to draw a line with a given character and length
    private static void drawLine(int n, char ch) {
        for (int i = 0; i < n; i++) {
            System.out.print(ch); //prints the character ntimes
        }
    }
    
    //for the B)
    //private method to draw a pyramid with a given size and total characters
    private static void drawPyramid2(int n, int total) { //total are characters in 
        int spacesBefore;
        int spacesAfter;
        for (int i = 0; i < n; i++) {
            spacesBefore = (total - (2 * i + 1)) / 2; //calculate the number of sapces before the pyramid
            spacesAfter = total - spacesBefore - (2 * i + 1); //calculate the number of spaces after the pyramid
            for (int j = 0; j < spacesBefore; j++) {
                System.out.print(".");//prints spaces before the pyramid
            }
            for (int j = 0; j < 2 * i + 1; j++) {
                System.out.print("X"); //prints the top part of the pyramid
            }
            for (int j = 0; j < spacesAfter; j++) {
                System.out.print("."); //print spaces after the pyramid
            }
            System.out.println(); //new line at the end of each row of the pyramid
        }
    }




    //used for the a)
    public static void drawPyramid(int n) {
        for (int i = n; i > 0; i--) {
            drawLine(2 * i - 1, 'X');// dras top part of pyramid
            System.out.println(); //new line at the end of each row of the pyramid
        }
        
        /*
         * for (int i = 0; i < n; i++) {
            drawLine(n - i - 1, '.');//spaces before the oyramid (n total size, i number of the actual line)
            drawLine(2 * i + 1, 'X');// dras top part of pyramid
            drawLine(n - i - 1, '.');// sapces after the pyamid
            System.out.println(); //new line at the end of each row of the pyramid
        }
         */
    }

    public static void drawChristmassTree(int n) {
        int totalLines = 2 * n - 1; //calculate the totl number of lines of the christmas tree
        for (int j = 1; j <= n; j++) {
            drawPyramid2(j, totalLines); //draws top part of ct
        }
    }
}
