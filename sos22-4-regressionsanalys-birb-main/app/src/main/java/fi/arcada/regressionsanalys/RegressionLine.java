package fi.arcada.regressionsanalys;

import java.lang.Math;

public class RegressionLine {

    // deklarera k, m, x  och correlationCoefficient som double

    double k, m, x, correlationCoefficient, n, X, Y, xy, xsum, ysum, xsquere, ysquere;
    int i, j;

    // Skapa en konstruktor som tar emot data-arrays för x och y

        public RegressionLine(double[] xVals, double[] yVals) {

            //double[] xtemp = {520, 610, 840, 540, 360, 260, 85};
            //double[] ytemp = {17, 22, 26, 19, 16, 15, 14};
            //double[] xVals = { 47,  42,  43,  42,  41,  48,  46,  44,  42,  43,  39,  43,  39,  42,  44,  45,  43,  44,  45,  42,  43,  32,  48,  43,  45,  45};
            //double[] yVals = { 194, 188, 181, 177, 182, 197, 179, 176, 177, 188, 164, 171, 170, 180, 171, 185, 179, 182, 180, 178, 178, 148, 197, 183, 179, 198};

            //antalet par i datamängden
            n = xVals.length;
            for(i = 0; i < xVals.length || j < yVals.length; i++, j++){
                X = xVals[i];
                Y = yVals[j];
                xy += X*Y;
                xsum += xVals[i];
                ysum += yVals[j];
                xsquere += xVals[i] * xVals[i];
                ysquere += yVals[i] * yVals[i];
            }

            //System.out.println(xy);
            //System.out.println(xsum);
            //System.out.println(ysum);
            //System.out.println(xsquere);
            k = ((n*xy)-(xsum*ysum))/((n*xsquere-(xsum*xsum)));
            //System.out.println(k);
            m = (ysum/n)-k*(xsum/n);
            //System.out.println(m);

            //x = (180 - m) / k;
            //System.out.println(x);


            correlationCoefficient = ((n*xy)-(xsum*ysum)) / (Math.sqrt(((n*xsquere)-(xsum*xsum))*((n*ysquere)-(ysum*ysum))));
            //System.out.println(correlationCoefficient);


        }

        public double getX(double yValue) {
            x = (yValue - m) / k;
            //System.out.println(yValue);
            return x;

        }

    // Uträkningen för k och m kan ske i konstruktorn m.h.a.

    // formeln för minsta kvadratmetoden
    // Del 3: uträkningen för correlationCoefficient kan också ske i konstruktorn
    // (det är förstås också ok att ha en skild metod för uträknigarna om man vill
    // hålla konstruktorn simpel.)

    // skapa en metod getX som tar emot ett y-värde, räknar ut x
    // m.h.a. räta linjens ekvation y=kx+m, och returnerar x

    // Del 3:
    // - skapa en getter-metod för correlationCoefficient
    // - skapa en String-metod getCorrelationGrade() för uträkning av korrelationsgrad

}