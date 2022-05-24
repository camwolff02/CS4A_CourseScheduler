package src.java;

abstract class Person {
    // class data
    private static int currId = 1000000;
    
    // for user to access
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String email;
    protected String telephone;
    protected int id;
    // address
    private String street;
    private String city;
    private String state;
    private int zip;

    public abstract void addCourse(String courseId);

    protected Person(String firstName, String middleName, String lastName, String email,
        String telephone, String street, String city, String state, int zip) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;

        id = generateNextId(id);
    }

    private static int generateNextId(int id) {
        return currId++;
    }

    @Override
    public String toString() {
        String str = String.format("name: %s %s %s, id: %d email: %s, telephone: %s, Address: %s %s %s, %d ", 
            firstName, middleName, lastName, id, email, telephone, street, city, state, zip);
        return str;
    }

    public String getName() { return String.format("%s %s %s", firstName, middleName, lastName); }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return this.middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return this.telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getStreet() { return this.street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return this.city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return this.state; }
    public void setState(String state) { this.state = state; }

    public int getZip() { return this.zip; }
    public void setZip(int zip) { this.zip = zip; }
}

