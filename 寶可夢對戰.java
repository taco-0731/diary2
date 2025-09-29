import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract class Monsterbase{
    public int attackPower;
    public int defensePower;
    public int healingPower;
    public int HP;
    public boolean isDefense;    
    public boolean isHealing;


    public abstract int attack(Monster target);
    public abstract int defense();
    public abstract int healing();
   
}

class Monster extends Monsterbase{
     public Monster(){
        this.attackPower=40;
        this.defensePower=20;
        this.healingPower=20;
        this.HP=200;
        this.isDefense = false;        
        this.isHealing = false;

    }
    
    public Monster(int attackPower,int defensePower,int healingPower,int HP){
        this.attackPower=attackPower;
        this.defensePower=defensePower;
        this.healingPower=healingPower;
        this.HP=HP;
        this.isDefense = false;
        this.isHealing = false;
    }

    @Override
    public int attack(Monster target){
        return this.attackPower;
    }

    @Override
    public int defense() {
        return this.defensePower;
    }

    @Override
    public int healing(){
        return this.healingPower;
    }

    @Override
    public String toString() {
        String monsterType;
        String isUpgrade;
        if (this.getClass().getName() == UpgradeMonsterFire.class.getName() || this.getClass().getName() == UpgradeMonsterElect.class.getName()) {
            monsterType = this.getClass().getSuperclass().getName();
            isUpgrade = "是";
        } else {
            monsterType = this.getClass().getName();
            isUpgrade = "否";
        }

        return String.format("\tMonster 種類: %s\n\tMonster 是否升級: %s\n\tMonster 剩餘血量: %s", monsterType, isUpgrade, this.HP);
    }
} 


class MonsterFire extends Monster{
    public MonsterFire(){
        super(50, 10, 30,200);
    }

    public MonsterFire(int attackPower, int defensePower, int healingPower, int HP){
        this.attackPower=attackPower;
        this.defensePower=defensePower;
        this.healingPower=healingPower;
        this.HP=HP;
        this.isDefense = false;
        this.isHealing = false;
    }

    @Override
    public int attack(Monster target){
        Random rand=new Random();
        int r=rand.nextInt(100);
        int damage;
        if(!target.isDefense){
            if(r<40){
                damage=attackPower;
            }
            else if(r<80){
                damage=(int)(attackPower*0.5);
            }
            else{
                damage=0;
            }
        }else{
            if(r<40){
                damage=(int)(attackPower*0.5);
            }
            else if(r<80){
                damage=(int)(attackPower*0.25);
            }
            else{
                damage=0;

            }
        }
        return damage;
        // System.out.print("對方失血:"+damage);
        // System.out.print("對方剩餘HP:"+target.HP);
    }
}

class MonsterElect extends Monster{
    public MonsterElect(){
        super(30,30,50,200);
    }

    public MonsterElect(int attackPower,int defensePower,int healingPower,int HP){
        this.attackPower=attackPower;
        this.defensePower=defensePower;
        this.healingPower=healingPower;
        this.HP=HP;
        this.isDefense = false;
        this.isHealing = false;
    }    

    
    @Override
    public int defense() {
        return (int)(this.defensePower*1.5);
    }
}



class User {
    String id;
    String name;
    String password;
    Monster [] myBabys = {new MonsterFire(), new MonsterElect()};

    User() {
        System.out.println("Class User's default constructor is executed:");
    }
    User(String id){
        this.id = id;
    }

    User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        System.out.println("Class User's 2nd constructor is executed:");
    }

    int Login(String gID, String gPWD) {
        if (this.id.equals((gID))) {
            return this.password.equals(gPWD) ? 0 : -3;
        } else {
            return -4;
        }
    }
    @Override
    public String toString() {
        return String.format("玩家名稱: %s\n玩家所選 Monster 資訊:", this.id);
    }
}

class UpgradeMonster extends Monster {
    public UpgradeMonster(Monster originalMonster){
        super(originalMonster.attackPower*2, originalMonster.defensePower*2, originalMonster.healingPower*2,originalMonster.HP*2);
    }
}

class UpgradeMonsterFire extends MonsterFire {
    public UpgradeMonsterFire(MonsterFire originalMonsterFire){
        super(originalMonsterFire.attackPower*2, originalMonsterFire.defensePower*2, originalMonsterFire.healingPower*2, originalMonsterFire.HP*2);
    }
}

class UpgradeMonsterElect extends MonsterElect {
    public UpgradeMonsterElect(MonsterElect originalMonsterElect){
        super(originalMonsterElect.attackPower*2, originalMonsterElect.defensePower*2, originalMonsterElect.healingPower*2, originalMonsterElect.HP*2);
    }
}

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        String myName;
        int monsterIndex;
        boolean isUpgrade;
        boolean isGameOver = false;
        String upgrade;
        String answer;
        int action;
        User playerA = null, playerB = null;
        Monster monsterA = null, monsterB = null;
        System.out.println("請選擇 (1)新遊戲 (2)載入");
        action = Integer.parseInt(scanner.nextLine());
        if (action == 1) {
            System.out.println("請輸入玩家A姓名:");
            myName = scanner.nextLine();
            playerA = new User(myName);
            System.out.println("請選擇怪獸 (1)火系怪獸 (2)雷系怪獸:");
            answer = scanner.nextLine();
            monsterA = playerA.myBabys[Integer.parseInt(answer) - 1];
            System.out.println("是否要進化 (y)是 (n)否:");
            upgrade = scanner.nextLine();
            isUpgrade = upgrade.equalsIgnoreCase("y"); 
            if (isUpgrade){
                if (monsterA instanceof MonsterFire) monsterA = new UpgradeMonsterFire((MonsterFire)monsterA); 
                if (monsterA instanceof MonsterElect) monsterA = new UpgradeMonsterElect((MonsterElect)monsterA); 
            } 
            else if (!upgrade.equalsIgnoreCase("n")){
                System.out.print("輸入錯誤,byebye");
            }
        

            System.out.println("請輸入電腦姓名:");
            myName = scanner.nextLine();
            playerB = new User(myName);
            int randomMonsterIndex = rand.nextInt(playerB.myBabys.length);
            monsterB = playerB.myBabys[randomMonsterIndex];
            System.out.println(String.format("電腦選擇怪獸:%s", monsterB.getClass().getName()));
            System.out.println("是否要進化 (y)是 (n)否:");
            upgrade = scanner.nextLine();
            isUpgrade = upgrade.equalsIgnoreCase("y"); 
            if (isUpgrade){
                if (monsterB instanceof MonsterFire) monsterB = new UpgradeMonsterFire((MonsterFire)monsterB);
                if (monsterB instanceof MonsterElect) monsterB = new UpgradeMonsterElect((MonsterElect)monsterB);
            } 
            else if (!upgrade.equalsIgnoreCase("n")){
                System.out.print("輸入錯誤,byebye");
            } 
        } else if (action == 2) {
            System.out.println("請選擇想要載入的檔案名稱:");
            String fileName = scanner.nextLine();
            System.out.println("載入遊戲中......");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));   
                int counter = 0;
                String playerName = "", monsterType = "", HP = "";
                isUpgrade = false;
                while (bufferedReader.ready()) {
                    String content = bufferedReader.readLine();
                    if (content.contains("玩家名稱")) {
                        playerName = content.split(":")[1].trim();
                    } else if (content.contains("Monster 種類")) {
                        monsterType = content.split(":")[1].trim();   
                    } else if (content.contains("Monster 是否升級")) {
                        isUpgrade = content.split(":")[1].trim().equals("是");
                    } else if (content.contains("Monster 剩餘血量")) {
                        HP = content.split(":")[1].trim();
                        if (counter == 0) {
                            playerA = new User(playerName);
                            if (monsterType.equals(MonsterFire.class.getName())) {
                                monsterA = playerA.myBabys[0];
                            } else {
                                 monsterA = playerA.myBabys[1];
                            } 
                            if (isUpgrade && monsterA instanceof MonsterFire) monsterA = new UpgradeMonsterFire((MonsterFire)monsterA);
                            if (isUpgrade && monsterA instanceof MonsterElect) monsterA = new UpgradeMonsterElect((MonsterElect)monsterA);
                            monsterA.HP = Integer.parseInt(HP);
                        } else {
                            playerB = new User(playerName);
                            if (monsterType.equals(MonsterFire.class.getName())) {
                                monsterB = playerA.myBabys[0];
                            } else {
                                monsterB = playerA.myBabys[0];
                            } 
                            if (isUpgrade) monsterB = new UpgradeMonster(monsterB);
                            monsterB.HP = Integer.parseInt(HP);
                        }
                        counter++;
                    }
                }
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        } else {
            try {                
                throw new Exception("error...");
            } catch(Exception e) {
                scanner.close();
                System.out.println(e.getMessage());                
            }
        }
        
        while (!isGameOver) {
            if (playerA == null || playerB == null || monsterA == null || monsterB == null) break;

            System.out.print("[主畫面] 玩家A請輸入功能選項 ([1] 攻擊 [2] 防守 [3] 補血 [4] 離開 [5] 存檔)：");
            action = scanner.nextInt();
            if (action == 1){
                monsterA.isDefense = false;
            } else if (action == 2) {
                monsterA.isDefense = true;
            } else if (action == 3) {
                monsterA.isHealing = true;
            } else if (action == 4) {
                break;
            } else if (action == 5) {
                try {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(String.format("./%s.txt", dateFormatter.format(new Date()))), "UTF-8");
                    outputStreamWriter.write(String.format("%s\n%s\n%s\n%s", playerA, monsterA, playerB, monsterB));
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("輸入錯誤");
                break;
            }

            System.out.print("[主畫面] 玩家B請輸入功能選項 ([1] 攻擊 [2] 防守 [3] 補血 [4] 離開 [5] 存檔)：");
            action = scanner.nextInt();
            if (action == 1){
                monsterB.isDefense = false;
            } else if (action == 2) {
                monsterB.isDefense = true;
            } else if (action == 3) {
                monsterB.isHealing = true;
            } else if (action == 4) {
                break;
            } else if (action == 5) {
                System.out.println("error! 電腦不能選擇存檔");
                break;
            }else {
                System.out.println("輸入錯誤");
                break;
            }

            int damage;
            if (!monsterA.isDefense){
                if (monsterB.isDefense){
                    damage = monsterA.attack(monsterB) - monsterB.defense();
                } else {
                    damage = monsterA.attack(monsterB);
                }
                monsterB.HP -= damage;
                System.out.println("MonsterA 發起攻擊!");
                System.out.println("MonsterB 血量剩下" + monsterB.HP);
                if (monsterB.HP <= 0) break;
            }
            
            if (!monsterB.isDefense){
                if (monsterA.isDefense){
                    damage = monsterB.attack(monsterA) - monsterA.defense();
                } else {
                    damage = monsterB.attack(monsterA);
                }
                monsterA.HP -= damage;
                System.out.println("MonsterB 發起攻擊!");
                System.out.println("MonsterA 血量剩下" + monsterA.HP);
                if (monsterA.HP <= 0) break;
            }

            if (monsterA.isHealing) {
                monsterA.HP += monsterA.healing();
                System.out.println(String.format("monsterA 回復了 %d 血量!", monsterA.healingPower));
                System.out.println(String.format("monsterA 剩餘血量: %d", monsterA.HP));
            }

            if (monsterB.isHealing) {
                monsterB.HP += monsterB.healing();
                System.out.println(String.format("monsterB 回復了 %d 血量!", monsterB.healingPower));
                System.out.println(String.format("monsterB 剩餘血量: %d", monsterB.HP));
            }
            



        }


        scanner.close();
    }

}

