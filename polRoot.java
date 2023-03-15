import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class polRoot {
    public static void main(String[] args) throws IOException
    {
        double tolerance = 0.000001;
        boolean bisection = true;
        boolean newton = false;
        boolean secant = false;
        boolean hybrid = false;

        int iterations = 10000;
        double initialPoint;
        double secondPoint = 0;
        String fileName;

        if (args[0].contains("-newt")){
            bisection = false;
            newton = true;
        }
        else if (args[0].contains("-sec")) {
            bisection = false;
            secant = true;
        }
        else if (args[0].contains("-hybrid")) {
            bisection = false;
            hybrid = true;
        }

        if (bisection == true)
        {
            int nextArgument;
            if (args[0].contains("-maxIt"))
            {
                iterations = Integer.parseInt(args[1]);
                initialPoint = Integer.parseInt(args[2]);
                nextArgument = 3;
            }
            else
            {
                initialPoint = Integer.parseInt(args[0]);
                nextArgument = 1;
            }

            if (args[nextArgument].contains(".pol"))
                fileName = args[nextArgument];
            else
            {
                secondPoint = Integer.parseInt(args[nextArgument]);
                fileName = args[nextArgument+1];
            }
        } else 
        {
            int nextArgument;
            if (args[1].contains("-maxIt"))
            {
                iterations = Integer.parseInt(args[2]);
                initialPoint = Integer.parseInt(args[3]);
                nextArgument = 4;
            }
            else
            {
                initialPoint = Integer.parseInt(args[1]);
                nextArgument = 2;
            }

            if (args[nextArgument].contains(".pol"))
                fileName = args[nextArgument];
            else
            {
                secondPoint = Integer.parseInt(args[nextArgument]);
                fileName = args[nextArgument+1];
            }
        }
        //////Read function
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        int degree = scan.nextInt();
        ArrayList<Double> arr = new ArrayList<Double>();

        while(scan.hasNextDouble())
        {
            arr.add(scan.nextDouble());
        }

        scan.close();

        //Get output file ready to be written to
        FileWriter writer = new FileWriter("fun1.sol");

        ///Call Methods

        if (bisection)
        {
            resultant result = bisec(initialPoint, secondPoint, iterations, degree, arr, tolerance);
            writer.write(result.toString());
            writer.close();
            System.out.println("Results file 'fun1.sol' has been created.");
        } else if (newton)
        {
            resultant result = newton(initialPoint, arr, iterations, degree, tolerance);
            writer.write(result.toString());
            writer.close();
            System.out.println("Results file 'fun1.sol' has been created.");
        } else if (secant)
        {
            resultant result = secant(initialPoint, secondPoint, iterations, degree, arr, tolerance);
            writer.write(result.toString());
            writer.close();
            System.out.println("Results file 'fun1.sol' has been created.");
        } else if (hybrid)
        {
            if (iterations > 10)
            {
                resultant firstPart = bisec(initialPoint, secondPoint, 10, degree, arr, tolerance);
                resultant secondPart = newton(secondPoint, arr, iterations-10, degree, tolerance);
                writer.write(firstPart.addResultants(secondPart));
                writer.close();
                System.out.println("Results file 'fun1.sol' has been created.");
            } else
            {
                resultant result = bisec(initialPoint, secondPoint, iterations, degree, arr, tolerance);
                writer.write(result.toString());
                writer.close();
                System.out.println("Results file 'fun1.sol' has been created.");
            }
        }
    }

    //Bisection
    public static resultant bisec(double initialPoint, double secondPoint, int iterations, int degree, ArrayList<Double> arr, double tolerance)
    {
        double a = Math.min(initialPoint, secondPoint);
        double b = Math.max(initialPoint, secondPoint);
        int counter = 0;
        double c;

        do {
            counter++;
            c = (a + b) / 2;

            if (func(c, degree, arr) > 0)
            {
                b = c;
            }
            else if (func(c, degree, arr) < 0)
            {
                a = c;
            } else
            {
                System.err.println("Error occurred");
                System.exit(1);
            }
        } while (Math.abs(func(c, degree, arr)) > tolerance && counter < iterations);

        return new resultant(c, iterations);
    }

    //Newton's
    public static resultant newton(double point, ArrayList<Double> arr, int iterations, int degree, double tolerance)
    {
        double newval = 0;
        int counter = 0;

        do
        {
            counter++;
            newval = point - (func(point, degree, arr) / func(point, degree-1, deriv(degree, arr)));
            point = newval;
        } while (func(newval, degree, arr) > tolerance && counter < iterations);

        return new resultant(point, iterations);
    }

    //Secant
    public static resultant secant(double initialPoint, double secondPoint, int iterations, int degree, ArrayList<Double> arr, double tolerance)
    {
        double newval = 0;
        int counter = 0;
        do
        {
            counter++;
            newval = secondPoint - (((secondPoint - initialPoint) * func(secondPoint, degree, arr)) / (func(secondPoint, degree, arr) - func(initialPoint, degree, arr)));
            initialPoint = secondPoint;
            secondPoint = newval;
        } while(Math.abs(func(newval, degree, arr)) > tolerance && counter < iterations);

        return new resultant(newval, counter);
    }

    //Return der as arraylist
    public static ArrayList<Double> deriv(int degree, ArrayList<Double> arr)
    {
        ArrayList<Double> der = new ArrayList<Double>();
        int element = 0;

        while(degree > 0)
        {
            der.add(degree * arr.get(element));
            element++;
            degree--;
        }

        return der;
    }

    //Evaluate x in function arr
    public static double func(double x, int degree, ArrayList<Double> arr)
    {
        int degreeCountdown = degree;
        double result = 0;
        int i = 0;

        while (degreeCountdown > -1)
        {
            result = result + (arr.get(i) * Math.pow(x, degreeCountdown));
            i++;
            degreeCountdown--;
        }

        return result;
    }
}