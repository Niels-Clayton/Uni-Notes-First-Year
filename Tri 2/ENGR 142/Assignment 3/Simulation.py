import math
import matplotlib.pyplot as plt

count = 0
mass = 5*(10**-4)
dragConstant = 2*(10**(-5))
tFinal = 10.0
numPts = 10000
acceleration = 0.0
previousVelocity = 0.0
totaldisplacment = 0.0
forceGravity = mass * 9.81


# Clacualte the terminal velocity of the hailstone as ait falls.
terminalVelocity = math.sqrt(forceGravity/dragConstant)

times = [] #array of times
velocityList = [] #array of velocities
displacments = [] #array of displacments

for x in range(numPts):
    times.append(x * (tFinal/numPts)) # Create an list of times


for x in range(numPts):
    # change in velocity
    deltaVelocity = acceleration * (tFinal/numPts)
    # current velocity
    velocity = previousVelocity + deltaVelocity
    # drag netForce using given velocity
    drag = dragConstant * velocity**2
    # total netForce on object (Fg - Fd)
    netForce = forceGravity - drag
    # acceleration on object
    acceleration = netForce / mass
    # previously calculated velocity used to find next velocity
    previousVelocity = velocity
    # add velocity to a list
    velocityList.append(velocity)

    # once the velocity is greater than 10% of the terminal velocity, print out the time at that point, only once.
    if velocity > (0.9 * terminalVelocity) and count == 0:
        print ('The time to reach 10% of the terminal velocity is {}\n'.format(times[x]))
        count = count+1
count = 0

# Displacment = velocity * time
for x in range(numPts):
    # The velocity at time x
    velocity = velocityList[x]
    # Time spent at that velocity
    time = (tFinal/numPts)
    # displacment over that time period
    displacment = velocity * time
    # Total displacment
    totaldisplacment += displacment
    # Add each displacment to an array
    displacments.append(totaldisplacment)
    # once the velocity is greater than 10% of the terminal velocity, print out the displacment at that point, only once.
    if velocity > (0.9*terminalVelocity) and count == 0:
        print ('The displacment at 10% of the terminal velocity is {}\n'.format(displacments[x]))
        count = count+1

print ('the velocity after {} seconds is {}' .format(tFinal, previousVelocity))
print ('the displacment after {} seconds is {}' .format(tFinal, totaldisplacment))

plt.subplot(1,2,1)
plt.plot(times,velocityList)
plt.ylabel('Velocity ms/s')
plt.xlabel('Time s')

plt.subplot(1,2,2)
plt.plot(times, displacments)
plt.ylabel('Displacment m')
plt.xlabel('Time s')
plt.show()
