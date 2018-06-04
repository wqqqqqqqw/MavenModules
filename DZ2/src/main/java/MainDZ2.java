public class MainDZ2 {

    public static void main(String[] args) {
        Person Igor = new Person(true, "Игорь");
        Person Nata = new Person(false, "Наташа");
        Person Vova = new Person(true, "Вова");
        Person Petya = new Person(true, "Петя");
        Person Marina = new Person(false, "Марина");

        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        Igor.marry(Nata);

        System.out.println(" ");
        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        Igor.marry(Nata);

        System.out.println(" ");
        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());
        System.out.println(Vova.getName() + " - " + Vova.getSpouseName());
        Igor.marry(Vova);

        System.out.println(" ");
        System.out.println(Marina.getName() + " - " + Marina.getSpouseName());
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        Marina.marry(Nata);

        System.out.println(" ");
        System.out.println(Marina.getName() + " - " + Marina.getSpouseName());
        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());
        Marina.marry(Igor);
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        System.out.println(Marina.getName() + " - " + Marina.getSpouseName());
        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());

        System.out.println(" ");
        Igor.marry(Marina);

        System.out.println(" ");
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        System.out.println(Petya.getName() + " - " + Petya.getSpouseName());
        Nata.marry(Petya);

        System.out.println(" ");
        System.out.println(Marina.getName() + " - " + Marina.getSpouseName());
        System.out.println(Petya.getName() + " - " + Petya.getSpouseName());
        Marina.marry(Petya);

        System.out.println(" ");
        System.out.println(Marina.getName() + " - " + Marina.getSpouseName());
        System.out.println(Petya.getName() + " - " + Petya.getSpouseName());
        System.out.println(Nata.getName() + " - " + Nata.getSpouseName());
        System.out.println(Igor.getName() + " - " + Igor.getSpouseName());
    }

}
