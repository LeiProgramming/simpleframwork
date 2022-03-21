package demo.annotation;

/**
 * @author Peter
 */
@CourseInfoAnnotation(courseName = "java", courseTag = "mainshi", courseProfile = "000", courseIndex = 303)
public class ImoocCourse {
    @PersonInfoAnnotation(name = "Peter", language = {"java","c++"})
    private String author;
    @CourseInfoAnnotation(courseName = "python", courseTag = "mainshi", courseProfile = "000", courseIndex = 303)
    public void getCourseInfo(){

    }
}
