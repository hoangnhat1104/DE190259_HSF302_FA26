package fu.de190259.entity;

/**
 * Enum đại diện cho giới tính nhân viên.
 * Được map xuống DB dưới dạng String (MALE / FEMALE / OTHER)
 * thông qua @Enumerated(EnumType.STRING) trên field gender của Employee.
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER
}
