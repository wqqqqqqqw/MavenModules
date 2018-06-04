
public class Person {

    private final boolean man;
    private final String name;
    private Person spouse;

    public boolean isMan() {
        return man;
    }

    public String getName() {
        return name;
    }

    public String getSpouseName() {
        if (spouse != null) {
            return spouse.name;
        } else {
            return "Не состоит в браке";
        }
    }

    public Person(boolean man, String name) {
        this.man = man;
        this.name = name;
    }

    public boolean marry(Person person) {
        if (this.man ^ person.man) {
            if (this.spouse != null) {
                if (this.spouse == person) {
                    System.out.println(this.name + " и " + person.name + " уже женаты");
                    return false;
                }
                System.out.println(this.name + " разводится с " + this.spouse.name);
                this.spouse.divorce();
                this.divorce();
            }
            if (person.spouse != null) {
                System.out.println(person.name + " разводится с " + person.spouse.name);
                person.spouse.divorce();
                person.divorce();
            }
            this.spouse = person;
            person.spouse = this;
            System.out.println(this.name + " и " + person.name + " поженились");
            return true;
        } else {
            System.out.println("Сочетаться могут только мужчина и женщина");
            return false;
        }
    }

    public boolean divorce() {
        if (spouse == null) {
            return false;
        } else {
            spouse = null;
            return true;
        }
    }
}