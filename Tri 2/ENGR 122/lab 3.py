
import numpy as np
import matplotlib.pyplot as plt
import math

x1 = 1.0
deltaMin = 0.001
IR = ((6.2*10**-9)*4700)

for i in range(0,10000):
    x2 = x1 - ((5-(IR*(math.exp(19.5*x1)-1))-x1)/(((-IR*19.5)*(math.exp(19.5*x1)))-1))
    print("%.4f" % x2)
    if abs(x2 - x1) < deltaMin:
        print("done")
        break
    x1 = x2
