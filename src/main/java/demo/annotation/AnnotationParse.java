package demo.annotation;

import java.lang.annotation.Annotation;

/**
 * @author Peter
 */
public class AnnotationParse {
    //解析类的注解
    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("demo.annotation.ImoocCourse");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations){
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
            System.out.println(courseInfoAnnotation.courseName()+courseInfoAnnotation.courseProfile());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        parseTypeAnnotation();
    }
}
