from numpy import matrix
from numpy.linalg import inv
import math

def reflect(theta, point):
    x = 1
    y = math.tan(theta)
    I = 1/((x**2)+(y**2))

    R = ((x**2)-(y**2), 2*x*y,2*x*y,(y**2)-(x**2))
    P = (point.item(0), point.item(1))

    R = matrix(R).reshape(2, 2)

    R = I*R
    PR = P*R
    return PR

def rotate(theta, point):
    R = (math.cos(theta), -math.sin(theta), math.sin(theta), math.cos(theta))
    R = matrix(R).reshape(2, 2)

    P = point

    return P*R

def stretch(stretch_x, stretch_y, point):
    S = (stretch_x, 0, 0, stretch_y)
    P = point

    S = matrix(S).reshape(2, 2)

    return P*S

def translate(move_x, move_y, point):
    P = point
    T = (move_x, move_y)

    T = matrix(T).reshape(1, 2)

    return (P+T)

def rotate_about(point, rotate_about, theta):
    P = point - rotate_about
    P = rotate(theta, P)
    return (P + rotate_about)

def challenge1(point, stretch_x, stretch_y, theta, move_x, move_y):
    P = point
    P = stretch(stretch_x, stretch_y, P)
    P = rotate(theta, P)
    P = translate(move_x, move_y, P)
    return P



P1 = (1,1)
P2 = (4,1)
P3 = (1,3)
P4 = (4,3)
RP = (2.5, 2) #the point we are rotating about, here it is the centre of the rectangle

P1 = matrix(P1).reshape(1,2)
P2 = matrix(P2).reshape(1,2)
P3 = matrix(P3).reshape(1,2)
P4 = matrix(P4).reshape(1,2)
RP = matrix(RP).reshape(1,2)

print rotate_about(P1, RP, math.pi/2)
print rotate_about(P2, RP, math.pi/2)
print rotate_about(P3, RP, math.pi/2)
print rotate_about(P4, RP, math.pi/2)
