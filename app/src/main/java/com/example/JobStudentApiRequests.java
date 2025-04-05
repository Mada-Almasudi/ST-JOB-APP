package com.example.students_job_app;

public interface JobStudentApiRequests {

    // You should handle in backend these things :
    // 1. email verification
    // 2. cv will be uploaded as a pdf when register student or update student
    // 3. when an advertiser send a status of accepted for an application,  you should add a bill for the student who applied
    //for the job no matter what is the amount for the bill " we have the choice for the amount "
    //4. advertiser should pay a bill after one week from register


    /**
     * login
     * @param String email /required / validation
     * @param String password /required / validation
     * @param int user_type
     *
     * @return Student or Advertiser
     */

    /**
     * register_student
     * @param String name /required
     * @param String nick_name /required
     * @param String user_name /required
     * @param String email /required / validation / *** verification ***
     * @param String password /required / validation
     * @param String phone /required
     * @param Date birth_date /required
     * @param int gender /required
     * @param String study_type /required
     * @param String study_place /required
     * @param Date start_study /required
     * @param Date end_study default not existed  /required
     * @param file cv /required
     *
     * @return Student + boolean study_is_going :if end_study is not existed
     */

    /**
     * update_student
     * @param int user_id /required
     * @param String name /optional
     * @param String nick_name /optional
     * @param String user_name /optional
     * @param String phone /optional
     * @param String study_type / optional
     * @param String study_place /optional
     * @param String end_study /optional
     * @param file cv /optional
     *
     * @return Student + boolean study_is_going :if end_study is not existed
     */

    /**
     * get_job_opportunities
     * @ no params
     *
     * @return List of Job Opportunities
     *  Every object should have this information:
     *  - int id;
     *  - String advertiser_name;// from advertiser table
     *  - String title;
     *  - String company_name;
     *  - String job_location;
     *   String position;
     *   String required_skills;
     *   String details;
     *   String created_at;
     */

    /**
     * get_job_applications
     * @param int student_id
     * note: form /JOB_REQUESTS TABLE/
     *
     * @return List of Job requests
     *  Every object should have this information:
     *  - int id;
     *  - String job_title;//from job_opportunities table
     *  - String job_location;//from job_opportunities table
     *  - String company_name;//from job_opportunities table
     *  - int status;
     *  - date created_at;
     */

    /**
     * apply_job
     * @param int job_id //required
     * @param int student_id //required
     *
     * @return
     * status of the api request in any shape you choose
     */

    /**
     * add_course
     * @param int student_id //required
     * @param String institution //required
     * @param String course_name //required
     * @param date start_date // required/ no validation
     * @param date end_date //required/ no validation
     *
     * @return
     * status of api request
     */

    /**
     * add_interest
     * @param int student_id
     * @param String interest
     *
     * @return
     * status of api request
     */

    /**
     * student_has_bill
     * @param int user_id
     *
     * @return
     * - boolean has_bill // this will be true if there is in student_bills table a record with false value for is_paid
     * - int bill_id
     * - int amount
     * - date created_at
     */

    /**
     * student_pay_bill
     * note: change is_paid value of this bill record to true
     * @param int user_id
     * @param int bill_id
     *
     * @return
     * status of api request
     */

    /**
     * get_courses
     * @param int user_id
     * @return List of courses from STUDENT_COURSES TABLE
     * Every Object should have this information:
     *  - int id     //id of record not user_id
     *  - String course_name
     *  - String institution
     *  - date start_date
     *  - date end_date
     */

    /**
     * get_interests
     * @param int user_id
     * @return List of interests from STUDENT_INTERESTS TABLE
     * Every Object should have this information:
     *  - int id     //id of record not user_id
     *  - String interest_name
     */

    /**
     * register_advertiser
     * @param String name // required
     * @param String phone //required
     * @param String email //required/validation/ verification
     * @param String password//required/validation
     * @param String website //required/ validation
     * @param String description//required
     * @param String address//required
     * @param int years_of_incorporation //required
     * @param String professional_fields //required
     *
     * @return Advertiser
     */

    /**
     * update_advertiser
     * @param int advertiser_id//required
     * @param String advertiser_name//optional
     * @param String phone //optional
     * @param String website //optional
     * @param String location//optional
     * @param String int years_of_incorporation //optional
     * @param String professional_fields //optional
     *
     * @return Advertiser
     */

    /**
     * post_job_opportunity
     * @param int advertiser_id //required
     * @param String title; //required
     * @param String company_name;  //required
     * @param String job_location;  //required
     * @param String position;  //required
     * @param String required_skills;  //required
     * @param String details;  //required
     * @param String created_at;  //required
     *
     * @return
     * status of api request
     */

    /**
     * get_posted_jobs
     * note: from JOB_OPPORTUNITIES TABLE which were posted by this advertiser
     * @param int advertiser_id
     *
     * @return List of JobOpportunities which were posted by this advertiser
     *  Every object should have this information:
     *   int id;
     *   String title;
     *   String company_name;
     *   String job_location;
     *   String position;
     *   String required_skills;
     *   String details;
     *   String created_at;
     */

    /**
     * delete_job_opportunity
     * @param int advertiser_id
     * @param int job_opportunity_id
     *
     * @return
     * status of api request
     */

    /**
     * get_job_requests
     * @param int advertiser_id
     * note: form /JOB_REQUESTS TABLE/ which were applied on jobs were posted by this advertiser
     *
     * @return List of Job requests
     *  Every object has this information:
     *  - int id;
     *  - String student_name//who applied for the job
     *  - String job_title;//from job_opportunities table
     *  - String job_location;//from job_opportunities table
     *  - String company_name;//from job_opportunities table
     *  - int status;
     *  - Date created_at;
     */

    /**
     * advertiser_has_bill
     * @param int advertiser_id
     *
     * @return
     * - boolean has_bill // this will be true if there is in advertiser_bills table a record with false value for is_paid
     * - int bill_id
     * - int amount
     * - date created_at
     */

    /**
     * advertiser_pay_bill
     * note: change is_paid value of this bill record to true
     * @param int advertiser_id
     * @param int bill_id
     *
     * @return
     * status of api request
     */

    /**
     * change_request_status
     * @param int advertiser_id
     * @param int job_request_id
     * @param int status
     *
     * @return
     * status of api request
     */


}
