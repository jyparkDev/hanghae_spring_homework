package bus;

class Bus {
    private static final int MAX_NUM = 40; // 최대승객
    private static long buscount; // 버스회사 버스 수
    private int currentNum; // 현재인원
    private int fare; // 비용
    private long busNum; // 버스 고유번호
    private int oilNum; // 기름량
    private int speed; // 속도
    private OperationStatus status ; // 버스상태 DRIVE, STOP

    Bus() {
        this(1200,100,0);
    }
    public Bus(int fare,  int oilNum,int speed) {
        this.currentNum = 0;
        this.fare = fare;
        this.busNum = ++buscount;
        this.oilNum = oilNum;
        this.speed = speed;
        drive();
    }

    /**
     * 운행 시작
     * 기름량이 10미만일 경우 운행정지상태
     */
    public void drive(){
        if(oilNum < 10){
            System.out.println("기름량이 부족하여 운행할 수 없습니다.");
            return;
        }else if(status == OperationStatus.운행 ){
            System.out.println("이미 운행중입니다");
            return;
        }
        status = OperationStatus.운행;
        System.out.println(busNum+"번 버스 운행을 시작합니다.");
        changeSpeed(10);
    }

    /**
     * 버스 상태 변경
     * @param status
     * @return
     */
    public void changeStatus(OperationStatus status) {
        if (status.name() == "운행") {
            drive();
            return;
        }
        this.status = status;
        speed = 0;
        System.out.println("차고지행으로 변경합니다.");
    }

    /**
     * 승객 탑승
     * @param num
     * @return String 탑승여부
     */
    public void ride(int num){
        if(status.name() == "차고지행"){
            System.out.println("현재 운행중이지 않습니다.");
        }
        else if(currentNum + num > MAX_NUM){
            System.out.println("탑승가능한 최대 인원을 초과했습니다.!!");
        }else if(num < 0){
            System.out.println("유효하지 않은 탑승인원 수 입니다.");
        }
        else{
            currentNum  += num;
            System.out.println("현재 탑승 인원 : " + currentNum  +"명, 잔여석 : " + (MAX_NUM - currentNum)+"입니다.");
        }
    }

    public void changeSpeed(int speed) {
        if(this.status.name() == "STOP") System.out.println("현재 운행중이지 않습니다.");
         else if (oilNum < 10) System.out.println("주유량을 확인해주세요.");
         else if (this.speed + speed < 0) System.out.println("현재 속도 : " + this.speed + "입니다. 변경 속도를 확인해주세요");
         else {
            this.speed += speed;
            System.out.println("현재 속도 : " + this.speed);
            if(this.speed == 0) changeStatus(OperationStatus.차고지행);
        }
    }

    public static long getBuscount() {
        return buscount;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public long getBusNum() {
        return busNum;
    }

    public int getOilNum() {
        return oilNum;
    }

    /**
     * 기름량 변경을 위한 setter 활용
     * 기름량이 10미만일 경우 운행정지
     * @param oilNum 변경할 기름량
     */
    public void setOilNum(int oilNum) {
        if(this.oilNum + oilNum < 10){
            System.out.println("주유량을 확인해주세요.");
            if(0 <= this.oilNum + oilNum) {
                this.oilNum += oilNum;
                if(status != OperationStatus.차고지행){
                    changeStatus(OperationStatus.차고지행);
                }
            }
            return;
        }
        this.oilNum += oilNum;
        System.out.println("기름량 : " + this.oilNum);
    }

    public int getSpeed() {
        return speed;
    }


    public OperationStatus getStatus() {
        return status;
    }

}
