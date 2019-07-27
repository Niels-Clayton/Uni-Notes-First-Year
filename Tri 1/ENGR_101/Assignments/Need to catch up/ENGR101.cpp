# include < stdio .h >
# include < time .h >
# include " E101 .h "

void switching(){

    int speed = 100000;
    while (true){
        write_digital(7,1);
        sleep1(0,speed);
        write_digital(7,0);
        sleep1(0,speed);
        speed = speed - 200;
        if(speed <=100){
            speed = 100;
        }
    }
}

int main (){
    init();
    sensor_motor(read_analog(0));
    }

}

void sensor_motor(int speed){
    if (speed > 200){
        set_motor(1, -100 -(speed/10));
        set_motor(2, -100 -(speed/10));
    }
    else{
        set_motor(1, 100 + speed/5;
        set_motor(2, 100 +speed/5));
    }
}
