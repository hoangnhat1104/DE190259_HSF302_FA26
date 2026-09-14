package fu.de190259.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * Entity đại diện cho nhân viên — ánh xạ tới bảng "employees" trong DB.
 * Bài không có Relationship Mapping: chỉ có 1 entity độc lập, không FK.
 */
@Entity
@Table(name = "employees")
public class Employee {

    /** PK — tự sinh bởi DB (IDENTITY strategy, MSSQL dùng IDENTITY column). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Họ và tên — bắt buộc, không được null. */
    @Column(nullable = false)
    private String fullName;

    /** Email — phải unique trên toàn bảng. */
    @Column(unique = true)
    private String email;

    /**
     * Lương — dùng BigDecimal để đảm bảo độ chính xác tài chính.
     * KHÔNG dùng double/float vì lỗi làm tròn floating-point.
     */
    private BigDecimal salary;

    /**
     * Giới tính — map bằng EnumType.STRING để DB lưu chuỗi "MALE"/"FEMALE"/"OTHER"
     * thay vì số thứ tự (ORDINAL). Tránh vỡ dữ liệu khi thêm/đổi vị trí enum value.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Ngày vào làm — JPA 2.2+ map thẳng LocalDate, không cần @Temporal.
     */
    private LocalDate hireDate;

    /** Trạng thái còn đang làm việc hay đã nghỉ. */
    private boolean active;

    /**
     * Số năm làm việc — tính từ hireDate đến ngày hiện tại.
     * @Transient: KHÔNG lưu xuống DB, chỉ tồn tại trong bộ nhớ Java.
     */
    @Transient
    private int yearsOfService;

    // ─── Constructors ──────────────────────────────────────────────────────────

    /** Constructor không tham số — BẮT BUỘC cho JPA để khởi tạo entity khi load từ DB. */
    public Employee() {
    }

    /**
     * Constructor tiện dụng — dùng khi tạo mới Employee trong code.
     *
     * @param fullName họ tên
     * @param email    email unique
     * @param salary   lương
     * @param gender   giới tính
     * @param hireDate ngày vào làm
     * @param active   còn làm việc không
     */
    public Employee(String fullName, String email, BigDecimal salary,
                    Gender gender, LocalDate hireDate, boolean active) {
        this.fullName = fullName;
        this.email    = email;
        this.salary   = salary;
        this.gender   = gender;
        this.hireDate = hireDate;
        this.active   = active;
    }

    // ─── Calculated field ──────────────────────────────────────────────────────

    /**
     * Tính số năm làm việc từ hireDate đến hôm nay.
     * Kết quả không lưu DB (@Transient), gọi method này mỗi lần cần dùng.
     *
     * @return số năm nguyên, 0 nếu hireDate là null
     */
    public int calculateYearsOfService() {
        if (hireDate == null) return 0;
        return Period.between(hireDate, LocalDate.now()).getYears();
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    // ─── toString ──────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", gender=" + gender +
                ", hireDate=" + hireDate +
                ", active=" + active +
                ", yearsOfService=" + calculateYearsOfService() +
                '}';
    }
}
