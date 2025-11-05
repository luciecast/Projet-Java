public class Bow implements Weapon {
    private String name = "Arc";
    private int damage = 15;

    public void display() {
        System.out.println(" "+
           "4   '.                                        \n"+
           "4    ^.                                       \n"+
           "4     $                                        \n"+
           "4     'b                                       \n"+
           "4      'b.                                     \n"+
           "4        $                                     \n"+
           "4        $r                                    \n"+
           "4        $F                                    \n"+
           "-$b========4========$b====*P=-                           \n"+
           "4       *$$F                                  \n"+
           "4        $$''                                  \n"+
           "4       .$F                                    \n"+
           "4       dP                                     \n"+
           "4      F                                       \n"+
           "4     @                                        \n"+
           "4    .                                         \n"+
           "4   J.                                         \n"+
           "4  '$$ " );
        System.out.println(name + " - Dégâts : " + damage);
    }

    public int getDamage() { return damage; }
    public String getName() { return name; }
}
