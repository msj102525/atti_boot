package org.ict.atti_boot.doctor.model.outputVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.atti_boot.doctor.jpa.entity.Career;
import org.ict.atti_boot.doctor.jpa.entity.DoctorTag;
import org.ict.atti_boot.doctor.jpa.entity.Education;
import org.ict.atti_boot.review.model.output.OutputReview;
import org.springframework.stereotype.Component;
import org.ict.atti_boot.doctor.jpa.entity.Doctor;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class DoctorDetail {
    private String doctorId;
    private String hospitalPhone;
    private String introduce;
    private String hospitalAddress;
    private String hospitalName;
    private String userName;
    private String profileUrl;
    private List<String> careers;
    private List<String> educations;
    private List<String> tags;
    private Double averageStarPoint;
    private List<OutputReview> reviews;
    private Map<Integer, Integer> ratingCount;
    private Boolean hasMoreReview;

    public DoctorDetail(Doctor doctor, List<OutputReview> reviews, Map<Integer, Integer> ratingCount, Double averageStarPoint, Boolean hasMoreReview, Set<Career> careers, Set<Education> educations, Set<DoctorTag> tags) {
        this.doctorId = doctor.getUserId();
        this.hospitalPhone = doctor.getHospitalPhone();
        this.introduce = doctor.getIntroduce();
        this.hospitalAddress = doctor.getHospitalAddress();
        this.hospitalName = doctor.getHospitalName();
        this.userName = doctor.getUser().getUserName();
        this.profileUrl = doctor.getUser().getProfileUrl();
        //캐리어 리스트 만들기
        List<String> careerList = new ArrayList<>();
        for (Career career : careers) {
            careerList.add(career.getCareer());
        }
        this.careers = careerList;
        List<String> educationList = new ArrayList<>();
        for (Education education : educations) {
            educationList.add(education.getEducation());
        }
        this.educations = educationList;
        List<String> tagList = new ArrayList<>();
        for (DoctorTag tag : tags) {
            tagList.add(tag.getTag());
        }
        this.tags = tagList;
        this.reviews = reviews;
        this.averageStarPoint = averageStarPoint;
        this.ratingCount = ratingCount;
        this.hasMoreReview = hasMoreReview;
    }


}

