import math
import matplotlib.pyplot as plt
import numpy as np

xi = 2.0
vi = 0.0

mass = 1.0
b = 0.4   #drag Constant
k = 4.0   #spring Constant

tInital = 0.0
tFinal = 40.0
numPts = 30000
dt = (tFinal - tInital)/numPts  #the time step (change in time between each point)


# Claculate the exact value using the formula, so that we can compare the two
angular_f = math.sqrt((k/mass)-(b**2/(4*mass**2)))
alpha = b/(2*mass)
phi = 0
amplitude = 2
time0 = np.linspace(tInital,tFinal,numPts) #creates an array of evenly spaced numbers, betweenthe inputs
x_exact = amplitude*np.exp(-alpha*time0)*np.cos(angular_f*time0+phi)



time = [tInital]    #list of times, starting at initial time
x_position = [xi]  #list of x-positions, starting at xi
velocity = [vi]     #list of velocities, starting at vi

for x in range(numPts - 1):

    springForce = -k*x_position[x]   # the force of the spring at given x position
    dragForce = -b*velocity[x]       # the force of drag at given velocity
    drivingForce = (2*math.cos(3*time[x]))
    acceleration = (springForce + dragForce + drivingForce)/mass # acceleration is net force/mass


    newTime = time[x] + dt              # increment time by the step
    time.append(newTime)
    dx = velocity[x]*dt                 #the change in x equals the velocity times the time
    new_x = x_position[x] + dx          #new x position is the old one plus the change in x
    dv = acceleration*dt                # the change in velocity equals acceleration times time
    new_v = velocity[x] + dv
    x_position.append(new_x)
    velocity.append(new_v)





plt.plot(time0,x_position)
plt.savefig("drivingForce Graph")

plt.show()
