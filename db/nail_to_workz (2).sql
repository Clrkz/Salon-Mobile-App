-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2019 at 12:25 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nail_to_workz`
--

-- --------------------------------------------------------

--
-- Table structure for table `accept_book`
--

CREATE TABLE `accept_book` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `date_accepted` date NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accept_book`
--

INSERT INTO `accept_book` (`id`, `user_id`, `book_id`, `date_accepted`, `status`) VALUES
(23, 33, 24, '2019-05-16', 1),
(24, 33, 22, '2019-05-16', 1),
(25, 33, 25, '2019-05-16', 0),
(26, 33, 25, '2019-05-16', 0),
(27, 33, 26, '2019-05-16', 0),
(28, 33, 25, '2019-05-16', 1),
(29, 33, 27, '2019-05-16', 1),
(30, 33, 29, '2019-05-16', 2),
(31, 33, 28, '2019-05-16', 1),
(32, 33, 30, '2019-05-16', 0);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(11) NOT NULL,
  `guest_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `member` int(11) NOT NULL,
  `date` varchar(50) NOT NULL,
  `time` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `dateadded` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `guest_id`, `service_id`, `member`, `date`, `time`, `status`, `dateadded`) VALUES
(1, 19, 1, 1, '05/31/2019', '5:56 PM', 1, '2019-05-08'),
(2, 17, 10, 0, '05/15/2019', '4:56 PM', 1, '2019-05-08'),
(3, 17, 7, 0, '05/16/2019', '5:01 PM', 1, '2019-05-08'),
(4, 17, 9, 0, '05/16/2019', '4:02 PM', 1, '2019-05-08'),
(5, 19, 9, 1, '', '', 1, '2019-05-09'),
(6, 19, 9, 1, '05/09/2019', '4:13 PM', 1, '2019-05-09'),
(7, 19, 8, 1, '05/08/2019', '5:31 PM', 1, '2019-05-09'),
(8, 17, 8, 0, '05/17/2019', '4:35 PM', 1, '2019-05-09'),
(9, 19, 8, 1, '05/17/2019', '5:13 PM', 1, '2019-05-09'),
(10, 19, 11, 1, '05/10/2019', '10:25 PM', 1, '2019-05-09'),
(11, 19, 10, 1, '05/09/2019', '7:30 PM', 1, '2019-05-09'),
(12, 19, 9, 1, '05/17/2019', '5:25 PM', 1, '2019-05-09'),
(13, 17, 10, 0, '05/10/2019', '3:15 PM', 1, '2019-05-09'),
(14, 17, 9, 0, '05/16/2019', '5:30 PM', 1, '2019-05-09'),
(15, 17, 9, 0, '05/10/2019', '11:33 PM', 1, '2019-05-09'),
(16, 21, 16, 0, '05/04/2019', '2:15 AM', 1, '2019-05-10'),
(17, 19, 7, 1, '05/11/2019', '7:55 AM', 1, '2019-05-10'),
(18, 21, 16, 0, '05/11/2019', '9:58 AM', 1, '2019-05-10'),
(19, 21, 16, 0, '05/11/2019', '9:58 AM', 1, '2019-05-10'),
(20, 21, 16, 0, '05/11/2019', '9:58 AM', 1, '2019-05-10'),
(21, 19, 16, 1, '', '', 1, '2019-05-16'),
(22, 19, 16, 1, '05/10/2019', '1:05 PM', 1, '2019-05-16'),
(23, 15, 16, 0, '05/24/2019', '5:07 PM', 1, '2019-05-16'),
(24, 15, 16, 0, '05/18/2019', '5:10 PM', 1, '2019-05-16'),
(25, 19, 16, 1, '05/16/2019', '2:39 PM', 2, '2019-05-16'),
(26, 19, 10, 1, '05/16/2019', '7:20 PM', 2, '2019-05-16'),
(27, 19, 10, 1, '05/17/2019', '3:52 PM', 1, '2019-05-16'),
(28, 19, 15, 1, '05/16/2019', '3:20 PM', 1, '2019-05-16'),
(29, 19, 16, 1, '05/25/2019', '3:54 PM', 1, '2019-05-16'),
(30, 19, 11, 1, '05/11/2019', '5:19 PM', 1, '2019-05-16');

-- --------------------------------------------------------

--
-- Table structure for table `booking_payment`
--

CREATE TABLE `booking_payment` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `code` varchar(999) NOT NULL,
  `image` varchar(999) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking_payment`
--

INSERT INTO `booking_payment` (`id`, `booking_id`, `code`, `image`) VALUES
(1, 2, '1001', '1557327441385.png'),
(2, 3, '105672', ''),
(3, 4, '232885', ''),
(4, 8, '46547964368', '1557387345104.png'),
(5, 13, '635263', '1557414951618.png'),
(6, 14, '46272', '1557415841991.png'),
(7, 15, 'ry', '1557416024970.png'),
(8, 16, 'eiieieei', ''),
(9, 18, 'hhkhkti', '1557449940517.png'),
(10, 19, 'hhkhkti', '1557449940517.png'),
(11, 20, 'hhkhkti', '1557449940517.png'),
(12, 23, '6272528262', '1557979667050.png'),
(13, 24, '638363', '1557979855212.png');

-- --------------------------------------------------------

--
-- Table structure for table `guest_information`
--

CREATE TABLE `guest_information` (
  `guest_id_PK` int(11) NOT NULL,
  `g_firstname` varchar(25) NOT NULL,
  `g_lastname` varchar(25) NOT NULL,
  `g_picture` varchar(70) DEFAULT NULL,
  `g_date_time_added` datetime NOT NULL,
  `g_gender` varchar(25) NOT NULL,
  `g_contact_number` varchar(30) NOT NULL,
  `g_fb_name` varchar(30) NOT NULL,
  `g_gmail` varchar(50) NOT NULL,
  `g_password` varchar(50) NOT NULL,
  `g_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guest_information`
--

INSERT INTO `guest_information` (`guest_id_PK`, `g_firstname`, `g_lastname`, `g_picture`, `g_date_time_added`, `g_gender`, `g_contact_number`, `g_fb_name`, `g_gmail`, `g_password`, `g_status`) VALUES
(14, 'udisi', 'udususi', '1556786183424.png', '2019-05-02 16:36:21', ' Male', 'ududu', '    udsdu', ' cust', '21232f297a57a5a743894a0e4a801fc3', 1),
(15, 'Clarence ', 'Andaya', '', '2019-05-08 16:41:23', 'Male', '091026', '143Clarkz', 'clrkz', '21232f297a57a5a743894a0e4a801fc3', 0),
(16, 'Juan', 'Ponce', '1557305192306.png', '2019-05-08 16:46:49', 'Male', 'Enrile', 'Ponce Enrile', 'Enrile', '21232f297a57a5a743894a0e4a801fc3', 0),
(17, 'Maria', 'Oasawa', '', '2019-05-08 17:00:39', 'Female', 'Bin laden', 'Osawa123', 'oasawa', '21232f297a57a5a743894a0e4a801fc3', 0),
(18, 'Magdalena', 'o', '', '2019-05-08 17:02:42', 'Female', 'Magda', 'Magda', 'magda', '21232f297a57a5a743894a0e4a801fc3', 0),
(19, 'Pusa', 'Cat', '1557308253448.png', '2019-05-08 17:37:56', 'Male', '928392', 'FB', 'Cat', '21232f297a57a5a743894a0e4a801fc3', 1),
(20, 'ad', 'asd', '', '2019-05-09 18:36:34', 'asfadf', 'asd', 'asd', 'sdsdf', 'd5597f9ec5d886d3bd30f1013ddd4496', 0),
(21, 'Jere', 'Isiah', '1557430771291.png', '2019-05-10 03:40:14', 'Male', '0900109999', 'facebook', 'jere', '845838210a77ea38b9a662d6f28fd7f3', 0);

-- --------------------------------------------------------

--
-- Table structure for table `salon_service`
--

CREATE TABLE `salon_service` (
  `salon_service_id` int(11) NOT NULL,
  `service_type` varchar(50) NOT NULL,
  `service_name` varchar(100) NOT NULL,
  `service_price` int(20) NOT NULL,
  `service_duration` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salon_service`
--

INSERT INTO `salon_service` (`salon_service_id`, `service_type`, `service_name`, `service_price`, `service_duration`) VALUES
(1, '1', 'Hair relax', 500, '2 hours'),
(2, '0', 'fsga', 0, 'jshaha'),
(3, '0', 'fsga', 0, 'jshaha'),
(4, '0', 'fsga', 0, 'jshaha'),
(5, '0', 'fsga', 0, 'jshaha'),
(6, '0', 'fsga', 0, 'jshaha'),
(7, 'Nail', 'sisss', 944, ' xxjs'),
(8, 'Hair', 'Clarence', 1, '1'),
(9, 'Foot', 'hsjs', 6464, 'hshs'),
(10, 'Hair', 'yzuz', 6467, 'zhzjz'),
(11, 'Hair', 'nzjz', 6767, 'snsj'),
(12, 'Hair', 'nzjz', 6767, 'snsj'),
(13, 'Hair', '', 0, ''),
(14, 'Hair', 'dkkd', 236, '56 hours'),
(15, 'Foot', 'uwuw', 2323, 'eyue'),
(16, 'Foot', 'Foot Spa', 326, '1 hour');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `hfrom` varchar(99) NOT NULL,
  `hto` varchar(99) NOT NULL,
  `date_close` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `hfrom`, `hto`, `date_close`) VALUES
(6, '11:34 PM', '4:34 PM', '05/15/2019');

-- --------------------------------------------------------

--
-- Table structure for table `user_information`
--

CREATE TABLE `user_information` (
  `u_information_id_PK` int(11) NOT NULL,
  `u_firstname` varchar(50) NOT NULL,
  `u_lastname` varchar(50) NOT NULL,
  `u_address` longtext NOT NULL,
  `u_picture` varchar(50) NOT NULL,
  `u_dateadded` date NOT NULL,
  `u_gender` varchar(50) NOT NULL,
  `u_fb_name` varchar(100) NOT NULL,
  `u_gmail` varchar(100) NOT NULL,
  `u_password` varchar(100) NOT NULL,
  `u_phone_number` varchar(50) NOT NULL,
  `u_availability` int(11) NOT NULL,
  `u_status` int(11) NOT NULL,
  `u_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_information`
--

INSERT INTO `user_information` (`u_information_id_PK`, `u_firstname`, `u_lastname`, `u_address`, `u_picture`, `u_dateadded`, `u_gender`, `u_fb_name`, `u_gmail`, `u_password`, `u_phone_number`, `u_availability`, `u_status`, `u_role`) VALUES
(31, 'Clarence', 'Andaya', 'bhkb', '1557223467427.png', '2019-05-07', 'Male', 'Clarence Andaya', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1234', 1, 1, 'Admin'),
(32, 'ajjaaai', 'kwkaaki', 'ahjaaj', '', '2019-05-10', 'Male', 'sjjssj', '54', 'a684eceee76fc522773286a895bc8436', 'sjissi', 1, 1, 'Admin'),
(33, 'jsjsj', 'jajaj', 'hajaaj', '1557456038904.png', '2019-05-10', 'Male', 'ueuw', 'staff', '1253208465b1efa876f982d8a9e73eef', 'idid', 1, 1, 'Staff');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `u_role_id_PK` int(11) NOT NULL,
  `u_role_title` varchar(25) NOT NULL,
  `u_role_description` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`u_role_id_PK`, `u_role_title`, `u_role_description`) VALUES
(1, 'Customer', 'Customer is always be number 1.'),
(2, 'Staff', 'Staff is person who manages the services of the customer'),
(3, 'Admin', 'Admin can check the behavior of the system');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accept_book`
--
ALTER TABLE `accept_book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `booking_payment`
--
ALTER TABLE `booking_payment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guest_information`
--
ALTER TABLE `guest_information`
  ADD PRIMARY KEY (`guest_id_PK`);

--
-- Indexes for table `salon_service`
--
ALTER TABLE `salon_service`
  ADD PRIMARY KEY (`salon_service_id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_information`
--
ALTER TABLE `user_information`
  ADD PRIMARY KEY (`u_information_id_PK`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`u_role_id_PK`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accept_book`
--
ALTER TABLE `accept_book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `booking_payment`
--
ALTER TABLE `booking_payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `guest_information`
--
ALTER TABLE `guest_information`
  MODIFY `guest_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `salon_service`
--
ALTER TABLE `salon_service`
  MODIFY `salon_service_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user_information`
--
ALTER TABLE `user_information`
  MODIFY `u_information_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `u_role_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
