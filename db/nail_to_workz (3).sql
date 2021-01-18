-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2019 at 02:15 PM
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
(14, 'udisi', 'udususi', '1556786183424.png', '2019-05-02 16:36:21', ' Male', 'ududu', '    udsdu', ' kl', '74b87337454200d4d33f80c4663dc5e5', 0);

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
(13, 'Hair', '', 0, '');

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
(31, 'Clarence', 'Andaya', 'bhkb', '1557223467427.png', '2019-05-07', 'Male', 'Clarence Andaya', 'admin', '74b87337454200d4d33f80c4663dc5e5', '1234', 1, 1, 'Admin');

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
-- AUTO_INCREMENT for table `guest_information`
--
ALTER TABLE `guest_information`
  MODIFY `guest_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `salon_service`
--
ALTER TABLE `salon_service`
  MODIFY `salon_service_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `user_information`
--
ALTER TABLE `user_information`
  MODIFY `u_information_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `u_role_id_PK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
