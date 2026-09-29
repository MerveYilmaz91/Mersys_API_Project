package Campus.Utilities;

public class Endpoints {

    public static final String LOGIN = "/auth/login";
    public static final String ACCOUNT = "/uaa/api/account";
    public static final String FEE_TYPES = "/school-service/api/fee-types";
    public static final String DISCOUNTS = "/school-service/api/discounts";
    public static final String BANK_ACCOUNTS = "/school-service/api/bank-accounts";

    public static final String STUDENT_GROUP = "/school-service/api/student-group";
    public static final String STUDENT_GROUP_BY_SCHOOL = STUDENT_GROUP + "/school/{schoolId}";
    public static final String STUDENT_GROUP_BY_ID = STUDENT_GROUP + "/{groupId}";
    public static final String ADD_STUDENTS = STUDENT_GROUP_BY_ID + "/add-students";
    public static final String REMOVE_STUDENTS = STUDENT_GROUP_BY_ID + "/remove-students";
    public static final String STUDENTS_OF_GROUP = "/school-service/api/students/group/{groupId}";

}
