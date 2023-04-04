# Bisection-Newton-Secant-App

Application to find the root of a polynomial. Input has to come from a .pol file that contains the coefficents with the degree of the polynomial on top. 4 ways to choose from: Bisection, Newton, Secant, or a Hybrid of Bisection and Newton.

The program uses bisection method by default and places the solution in a file with called fun1, but with extension .sol, with format:

root: 
iterations:

Operates as follows from command prompt:
> polRoot [-newt, -sec, -hybrid] [-maxIt n] initP [initP2] polyFileName

Examples in results.pdf file.
- Has to be run in command prompt
