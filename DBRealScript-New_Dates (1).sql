-- -------------------
-- Schema culinary_db
-- -------------------
DROP SCHEMA IF EXISTS culinary_db;
CREATE SCHEMA IF NOT EXISTS culinary_db;
USE culinary_db;

--
-- Table mentor
--

DROP TABLE IF EXISTS mentor;
CREATE TABLE IF NOT EXISTS mentor
( 
mentor_id INT PRIMARY KEY NOT NULL,
last_name VARCHAR(45) NOT NULL,
first_name VARCHAR(45) NOT NULL,
middle_initial VARCHAR(2),
age INT(2),
contact BIGINT(10)
);

--
-- Table training_program
--

DROP TABLE IF EXISTS training_program;
CREATE TABLE IF NOT EXISTS training_program
(
program_name ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') PRIMARY KEY NOT NULL,
start_date DATE NOT NULL,
end_date DATE UNIQUE NOT NULL,
cost FLOAT NOT NULL,
venue VARCHAR(45) NOT NULL,
class_limit INT NOT NULL
);

--
-- Table attendance
--

DROP TABLE IF EXISTS attendance;
CREATE TABLE IF NOT EXISTS attendance
(
attendance_report_id INT PRIMARY KEY NOT NULL,
attendance_date DATE NOT NULL,
mentor_id INT NOT NULL,
present_mentor TINYINT(1) NULL,
training_program ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
INDEX `ind_attendance_training_program` (training_program),
INDEX `ind_attendance_mentor` (mentor_id),
CONSTRAINT `fk_attendance_training_program` 
FOREIGN KEY(training_program)
REFERENCES culinary_db.training_program(program_name),
CONSTRAINT `fk_attendance_mentor` 
FOREIGN KEY(mentor_id)
REFERENCES culinary_db.mentor(mentor_id)
);

--
-- Table sections
--

DROP TABLE IF EXISTS sections;
CREATE TABLE IF NOT EXISTS sections
(
section_id INT PRIMARY KEY NOT NULL,
training_program ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
INDEX `ind_sections_training_program` (training_program),
CONSTRAINT `fk_sections_training_program` 
FOREIGN KEY(training_program)
REFERENCES culinary_db.training_program(program_name)
);

--
-- Table trainee
--

DROP TABLE IF EXISTS trainee;
CREATE TABLE IF NOT EXISTS trainee
(
trainee_id INT PRIMARY KEY NOT NULL,
last_name VARCHAR(45) NOT NULL,
first_name VARCHAR(45) NOT NULL,
middle_initial_name VARCHAR(1) NOT NULL,
age INT(2) NOT NULL,
contact BIGINT(10) NOT NULL,
training_program ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
section_id INT NOT NULL,
INDEX `ind_trainee_training_program` (training_program),
INDEX `ind_trainee_section_id`(section_id),
CONSTRAINT `fk_trainee_training_program` 
FOREIGN KEY(training_program)
REFERENCES culinary_db.training_program(program_name),
CONSTRAINT  `fk_trainee_section_id`
FOREIGN KEY (section_id)
REFERENCES culinary_db.sections (section_id)
);

--
-- Table approved_courses
--

DROP TABLE IF EXISTS approved_courses;
CREATE TABLE IF NOT EXISTS approved_courses
(
mentor_id INT PRIMARY KEY NOT NULL,
approved_french TINYINT(1) NOT NULL,
approved_japanese TINYINT(1) NOT NULL,
approved_greek TINYINT(1) NOT NULL,
approved_filipino TINYINT(1) NOT NULL,
approved_italian TINYINT(1) NOT NULL,
INDEX `ind_approved_courses_mentor_id` (mentor_id),
CONSTRAINT `fk_approved_courses_mentor_id`
FOREIGN KEY (mentor_id)
REFERENCES culinary_db.mentor (mentor_id)
);

--
-- Table schedule
--

DROP TABLE IF EXISTS program_schedule;
CREATE TABLE IF NOT EXISTS program_schedule 
(
training_program ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') PRIMARY KEY NOT NULL,
class_mon TINYINT(1) NOT NULL,
class_tue TINYINT(1) NOT NULL,
class_wed TINYINT(1) NOT NULL,
class_thu TINYINT(1) NOT NULL,
class_fri TINYINT(1) NOT NULL,
class_sat TINYINT(1) NOT NULL,
INDEX `ind_schedule_training_program` (training_program),
CONSTRAINT `fk_schedule_training_program`
FOREIGN KEY (training_program)
REFERENCES culinary_db.training_program(program_name)
);

--
-- Table enrollment
--

DROP TABLE IF EXISTS enrollment;
CREATE TABLE IF NOT EXISTS enrollment
(
enrollment_id INT PRIMARY KEY NOT NULL,
trainee_id INT NOT NULL,
training_program ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
enrollment_date DATE NOT NULL,
finish_date DATE UNIQUE NULL,
INDEX `ind_enrollment_trainee_id` (trainee_id),
INDEX `ind_enrollment_training_program` (training_program),
INDEX `ind_enrollment_finish_date` (finish_date),
CONSTRAINT `fk_enrollment_trainee_id`
FOREIGN KEY (trainee_id)
REFERENCES	culinary_db.trainee (trainee_id),
CONSTRAINT `fk_enrollment_training_program`
FOREIGN KEY (training_program)
REFERENCES culinary_db.training_program (program_name),
CONSTRAINT `fk_enrollment_finish_date`
FOREIGN KEY (finish_date)
REFERENCES culinary_db.training_program (end_date)
);

--
-- Table trainee_assessment
--

DROP TABLE IF EXISTS trainee_assessment;
CREATE TABLE IF NOT EXISTS trainee_assessment
(
assessment_id INT PRIMARY KEY NOT NULL,
evaluation_date DATE NULL,
trainee_id INT NOT NULL,
mentor_id INT NOT NULL,
evaluation_score ENUM('0.0', '1.0', '1.5', '2.0', '2.5', '3.0', '3.5', '4.0') UNIQUE NULL,
explanation VARCHAR(500) NULL,
INDEX `ind_trainee_assessment_mentor_id` (mentor_id),
INDEX `ind_trainee_assessment_trainee_id` (trainee_id),
CONSTRAINT	`fk_trainee_assessment_mentor_id` 
FOREIGN KEY (mentor_id)
REFERENCES culinary_db.mentor (mentor_id),
CONSTRAINT `fk_trainee_assessment_trainee_id`
FOREIGN KEY (trainee_id)
REFERENCES culinary_db.trainee (trainee_id)
);

--
-- Table receipt
--

DROP TABLE IF EXISTS receipt;
CREATE TABLE IF NOT EXISTS receipt
(
or_no INT PRIMARY KEY NOT NULL,
transaction_date DATE NOT NULL,
total_amount FLOAT NOT NULL
);
--
-- Table clearance_report
--

DROP TABLE IF EXISTS clearance_report;
CREATE TABLE IF NOT EXISTS clearance_report
(
clearance_report_id INT PRIMARY KEY NOT NULL,
clearance_status ENUM('paid', 'unpaid') NOT NULL,
trainee_id INT NOT NULL,
program_name ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
clearance_date DATE NULL,
receipt_or_no INT NULL,
INDEX `ind_clearance_report_receipt_or_no` (receipt_or_no),
INDEX `ind_clearance_report_trainee_id` (trainee_id),
INDEX `ind_clearance_report_program_name` (program_name),
CONSTRAINT `fk_clearance_report_receipt_or_no`
FOREIGN KEY (receipt_or_no)
REFERENCES culinary_db.receipt (or_no),
CONSTRAINT `fk_clearance_report_trainee_id`
FOREIGN KEY (trainee_id)
REFERENCES culinary_db.trainee (trainee_id),
CONSTRAINT `fk_clearance_report_program_name`
FOREIGN KEY (program_name)
REFERENCES culinary_db.training_program (program_name)
);

--
-- Table trainees_present
--

DROP TABLE IF EXISTS trainees_present;
CREATE TABLE IF NOT EXISTS trainees_present
(
attendance_report_id INT NOT NULL,
section_id INT NOT NULL,
trainee_id INT NOT NULL,
PRIMARY KEY (attendance_report_id,section_id,trainee_id),
INDEX `ind_trainees_present_attendance_report_id` (attendance_report_id),
INDEX `ind_trainees_present_section_id` (section_id),
INDEX `ind_trainees_present_trainee_id` (trainee_id),
CONSTRAINT `fk_trainees_present_attendance_report_id`
FOREIGN KEY (attendance_report_id)
REFERENCES	culinary_db.attendance (attendance_report_id),
CONSTRAINT `fk_trainees_present_section_id`
FOREIGN KEY (section_id)
REFERENCES	culinary_db.sections (section_id),
CONSTRAINT `fk_trainees_present_trainee_id`
FOREIGN KEY (trainee_id)
REFERENCES	culinary_db.trainee (trainee_id)
);

--
-- Table mentor_trainingprograms
--

DROP TABLE IF EXISTS mentor_trainingprograms;
CREATE TABLE IF NOT EXISTS mentor_trainingprograms
(
mentor_id INT NOT NULL,
program_name ENUM('french_cuisine','japanese_cuisine','greek_cuisine','filipino_cuisine','italian_cuisine') NOT NULL,
PRIMARY KEY (mentor_id, program_name),
INDEX `ind_mentor_trainingprograms_program_name` (program_name),
CONSTRAINT `fk_mentor_trainingprograms_program_name`
FOREIGN KEY (program_name)
REFERENCES culinary_db.training_program (program_name),
CONSTRAINT `fk_mentor_trainingprograms_mentor_id`
FOREIGN KEY (mentor_id)
REFERENCES culinary_db.mentor (mentor_id)

);

-- mentor
INSERT INTO mentor (mentor_id, last_name, first_name, middle_initial, age, contact)
VALUES
	(99901,'Moureau','Jaune','C', 33, 0917348910), -- mentors always have an id starting with 999
	(99902,'Ishikawa','Haraguchi','D', 54, 0917672890),
    (99903,'Adonis','Hellen', 'M', 36, 0925743476),
    (99904,'Del Rosario', 'Kevin', 'L', 25, 0967436781),
    (99905,'Giovanno','Vito','B', 48, 0988560621);

-- training_program
INSERT INTO training_program (program_name, start_date, end_date, cost, venue, class_limit)
VALUES
	('french_cuisine', '2023-10-02', '2023-09-21', 90000.00, 'EU1046', 30), -- programs are 11 weeks long, mon programs end on thurs 
    ('japanese_cuisine', '2023-04-04', '2023-06-23', 85000.00, 'AS204', 15), -- tues programs end on fri
    ('greek_cuisine', '2023-01-02', '2023-03-23', 63000.00, 'AS304', 35), -- monday and thurs
    ('filipino_cuisine', '2023-07-04', '2023-09-15', 78000.00, 'AS602', 40), -- tues and fri
    ('italian_cuisine', '2023-10-04', '2023-12-24', 83000.00, 'EU407', 25); -- wed programs end on sat

-- attendance
INSERT INTO attendance (attendance_report_id, attendance_date, mentor_id, present_mentor, training_program)
VALUES
	(2301,'2023-04-25', 99902, 1, 'japanese_cuisine'), -- Tuesday
    (2302,'2023-07-25', 99904, 1, 'filipino_cuisine'), -- Tuesday
    (2303,'2023-02-16', 99903, 1, 'greek_cuisine'), -- Thursday
    (2304,'2023-10-16', 99901, 1, 'french_cuisine'), -- Monday
    (2305,'2023-11-15', 99905, 1, 'italian_cuisine'); -- Wednesday

-- sections
INSERT INTO sections (section_id, training_program)
VALUES
	(00, 'french_cuisine'), -- section id's are only two digits (like in lasalle hehe)
    (10, 'japanese_cuisine'), -- the id's are spaced 10 apart to make space for 10 sections for each cuisine
    (20, 'greek_cuisine'),
    (30, 'filipino_cuisine'),
    (40, 'italian_cuisine');

-- trainee
INSERT INTO trainee (trainee_id, last_name, first_name, middle_initial_name, age, contact, training_program, section_id)
VALUES
	(219812, 'Bernard', 'Louis', 'C', 18, 0912556787, 'japanese_cuisine', 10), -- trainee id's always start with the last 2 digits of the year they first enrolled so '22' = 2022
    (223692, 'Jianna', 'Kimmy', 'P', 18, 0919828434, 'filipino_cuisine', 30), 
    (227279, 'Tarrick', 'Kristyan', 'E', 19, 0916875675, 'greek_cuisine', 20),
    (231967, 'Calugang', 'Gary', 'N', 19, 0916235627, 'french_cuisine', 00),
    (234678, 'Ralph', 'Mickey', 'W', 20, 0917878901, 'italian_cuisine', 40);

-- approved_courses
INSERT INTO approved_courses(mentor_id, approved_french, approved_japanese, approved_greek, approved_filipino, approved_italian)
VALUES
	(99901, 1, 0, 1, 0, 1),
    (99902, 0, 1, 0, 0, 1),
    (99903, 0, 0, 1, 0, 1),
    (99904, 0, 0, 0, 1, 0),
    (99905, 1, 0, 0, 0, 1);

-- program_schedule
INSERT INTO program_schedule(training_program, class_mon, class_tue, class_wed, class_thu, class_fri, class_sat)
VALUES
	('french_cuisine', 1,0,0,1,0,0),
    ('japanese_cuisine', 0,1,0,0,1,0),
    ('greek_cuisine', 1,0,0,1,0,0),
    ('filipino_cuisine', 0,1,0,0,1,0),
    ('italian_cuisine', 0,0,1,0,0,1);

-- enrollment
INSERT INTO enrollment(enrollment_id, trainee_id, training_program, enrollment_date)
VALUES
	(123556, 219812, 'japanese_cuisine', '2023-03-04'), -- enrollment starts exactly 1 month before program start
    (167899, 223692, 'filipino_cuisine', '2023-06-04'),
    (215644, 227279, 'greek_cuisine', '2022-12-02'),
    (242565, 231967, 'french_cuisine', '2023-09-02'),
    (389072, 234678, 'italian_cuisine', '2023-09-04');

-- trainee_assessment
INSERT INTO trainee_assessment (assessment_id, evaluation_date, trainee_id, mentor_id, evaluation_score, explanation)
VALUES
	(12,'2023-06-24', 219812, 99902, '2.0', 'Could use more practice with precise knife cuts, there are numerous wasted parts of fish because of imprecise cuts'),
    (252,'2023-09-16', 223692, 99904, '3.5', 'Exemplary in cooking traditional dishes like adobo and sinigang. Makes use of various spices to enhance dish flavors'),
    (702,'2023-03-24', 227279, 99903, '2.5', 'Is able to maximize flavor with minimal ingredients like for shakshuka, thought often adds too much oil to dishes'),
    (905,NULL, 231967, 99901, NULL, NULL),
    (1210,NULL, 234678, 99905, NULL, NULL);

-- receipt
INSERT INTO receipt(or_no, transaction_date, total_amount)
VALUES
	(202379257, '2023-04-12', 85000.00),
    (202379375, '2023-07-25', 78000.00),
    (202379678, '2023-02-12', 63000.00),
    (202379767, '2023-10-16', 45000.00),
    (202379844, '2023-10-14', 60000.00);
    
-- clearance_report
INSERT INTO clearance_report (clearance_report_id, clearance_status, trainee_id, program_name, clearance_date, receipt_or_no)
VALUES
	(219812, 'paid', 219812, 'japanese_cuisine', '2023-04-12' , 202379257),
    (223692, 'paid', 223692, 'filipino_cuisine', '2023-07-25', 202379375),
    (237279, 'paid', 227279, 'greek_cuisine', '2023-02-12', 202379678),
    (241967, 'unpaid', 231967, 'french_cuisine', NULL ,202379767),
    (254678, 'unpaid', 234678, 'italian_cuisine',  NULL, 202379767);

-- trainees_present
INSERT INTO trainees_present (attendance_report_id, section_id, trainee_id)
VALUES
	(2301,10,219812),
    (2302,30,223692),
    (2303,20,227279),
    (2304,00,231967),
    (2305,40,234678);

-- mentor_trainingprograms 
INSERT INTO mentor_trainingprograms (mentor_id, program_name)
VALUES 
	(99901,'french_cuisine'),
    (99902,'japanese_cuisine'),
    (99903,'greek_cuisine'),
    (99904,'filipino_cuisine'),
    (99905,'italian_cuisine');