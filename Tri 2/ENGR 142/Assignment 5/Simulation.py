import math
import matplotlib.pyplot as plt
import numpy as np

xi = 2.0
vi = 0.0

mass = 1.0
b = 0.1   #drag Constant
k = 4.0   #spring Constant

tInital = 0.0
tFinal = 80.0
numPts = 30000
dt = (tFinal - tInital)/numPts  #the time step (change in time between each point)


# Claculate the exact value using the formula, so that we can compare the two
# angular_f = math.sqrt((k/mass)-(b**2/(4*mass**2)))
# print angular_f
# alpha = b/(2*mass)
# phi = 0
# amplitude = 2
# time0 = np.linspace(tInital,tFinal,numPts) #creates an array of evenly spaced numbers, betweenthe inputs
# x_exact = amplitude*np.exp(-alpha*time0)*np.cos(angular_f*time0+phi)



forceDriving = 5.0
frequencyDriving = 20.0
time = [tInital]                     #list of times, starting at initial time
x_position = [xi]                    #list of x-positions, starting at xi
velocity = [vi]                      #list of velocities, starting at vi
max_A = 0.0


for x in range(numPts - 1):

    springForce = -k*x_position[x]   # the force of the spring at given x position
    dragForce = -b*velocity[x]       # the force of drag at given velocity
    drivingForce = (forceDriving*math.cos(frequencyDriving*time[x]))
    acceleration = (springForce + dragForce + drivingForce)/mass # acceleration is net force/mass


    newTime = time[x] + dt              # increment time by the step
    time.append(newTime)
    dx = velocity[x]*dt                 #the change in x equals the velocity times the time
    new_x = x_position[x] + dx          #new x position is the old one plus the change in x
    dv = acceleration*dt                # the change in velocity equals acceleration times time
    new_v = velocity[x] + dv
    x_position.append(new_x)
    velocity.append(new_v)

    if(time[x] > 60):                   # find the amplitude of the driven occilations
        if(new_x > max_A):
            max_A = new_x
print (max_A)

maximumA = 0.0
totalMaxA = 0.0
bestFrequency = 0.0
amplitudes = []
freqList = np.linspace(0.6,6,100000)
for i in range(1000):
    frequency = freqList[i]
    time_ = [tInital]                  #list of times, starting at initial time
    x_position_ = [xi]                 #list of x-positions, starting at xi
    velocity_ = [vi]                   #list of velocities, starting at vi

    for x in range(numPts - 1):

        springForce = -k*x_position_[x]   # the force of the spring at given x position
        dragForce = -b*velocity_[x]       # the force of drag at given velocity
        drivingForce = (forceDriving*math.cos(frequency*time_[x]))
        acceleration = (springForce + dragForce + drivingForce)/mass # acceleration is net force/mass


        newTime = time_[x] + dt              # increment time by the step
        time_.append(newTime)
        dx = velocity_[x]*dt                 # the change in x equals the velocity times the time
        new_x = x_position_[x] + dx          # new x position is the old one plus the change in x
        dv = acceleration*dt                 # the change in velocity equals acceleration times time
        new_v = velocity_[x] + dv
        x_position_.append(new_x)
        velocity_.append(new_v)

        if(time_[x] > 60): # find the amplitude of the driven occilations
            if(new_x > maximumA):
                maximumA = new_x

    amplitudes.append(maximumA)
    if(totalMaxA < maximumA):
        totalMaxA = maximumA
        bestFrequency = freqList[i]
    maximumA = 0

print ('The frequency that gives the maximum amplitude is {}\n'.format(bestFrequency))

plt.plot(time,x_position)
plt.ylabel('Amplitude')
plt.xlabel('Time s')
plt.show()

plt.plot(freqList,amplitudes)
plt.ylabel('Amplitude')
plt.xlabel('Driving angular frequency')
plt.show()
