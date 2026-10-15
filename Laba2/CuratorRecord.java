import java.util.Objects;

public class CuratorRecord {
    private final String lastName;
    private final String firstName;
    private final String birthDate;
    private final String phone;
    private final String address;

    public CuratorRecord(String lastName, String firstName, String birthDate, String phone, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuratorRecord record = (CuratorRecord) o;
        return Objects.equals(lastName, record.lastName) &&
               Objects.equals(firstName, record.firstName) &&
               Objects.equals(birthDate, record.birthDate) &&
               Objects.equals(phone, record.phone) &&
               Objects.equals(address, record.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, birthDate, phone, address);
    }

    @Override
    public String toString() {
        return String.format("Студент: %s %s | Д/Н: %s | Тел: %s | Адреса: %s",
                lastName, firstName, birthDate, phone, address);
    }
}