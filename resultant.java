public class resultant {
    private double root;
    private int iterations;
    
    public resultant(double root, int iterations)
    {
        this.root = root;
        this.iterations = iterations;
    }

    @Override
    public String toString()
    {
        return "Root: " + root + "\nIterations: " + iterations;
    }

    public int getIter()
    {
        return iterations;
    }

    public double getRoot()
    {
        return root;
    }

    public String addResultants(resultant secondResultant)
    {
        return "Root: " + (root + secondResultant.root)/2 + "\nIterations: " + (iterations + secondResultant.iterations);
    }
}
