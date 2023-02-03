package bus;

public class BusMain {
    public static void main(String[] args) {
        System.out.println("=== 버스 인스턴스 생성 및 버스고유번호 TEST ===");
        Bus bus1 = new Bus();
        Bus bus2 = new Bus(1500,100,0);
        System.out.println("\n=== 버스 속도 변경 TEST ===");
        bus1.changeSpeed(50);
        bus1.changeSpeed(-30);
        bus1.changeSpeed(-40);
        bus1.changeSpeed(-30);
        bus1.changeSpeed(10);
        System.out.println("\n=== 승객 탑승 TEST ===");
        System.out.println("현재인원 : " + bus2.getCurrentNum());
        bus2.ride(20);
        bus2.ride(21);
        bus2.ride(20);
        bus2.ride(20);
        bus2.ride(-1);
        System.out.println("\n=== 상태 변경 TEST ===");
        bus1.changeStatus(OperationStatus.운행);
        bus1.changeSpeed(20);
        bus1.changeStatus(OperationStatus.차고지행);
        bus1.changeSpeed(20);
        System.out.println("\n=== 운행 TEST ===");
        bus1.drive();
        bus1.drive();
        System.out.println("\n=== 기름량 TEST ===");
        bus1.setOilNum(50);
        bus1.setOilNum(-100);
        bus1.setOilNum(-60);
        bus1.setOilNum(-40);
        bus1.setOilNum(-11);
        bus1.setOilNum(-5);
        bus1.drive();
        bus1.setOilNum(-5);
        System.out.println(bus1.getOilNum());
        bus1.setOilNum(5);
        bus1.drive();
        bus1.setOilNum(5);
        bus1.drive();
        System.out.println("상태 : "+bus1.getStatus());
        bus1.changeStatus(OperationStatus.차고지행);
        System.out.println("상태 : "+bus1.getStatus());
    }
}
