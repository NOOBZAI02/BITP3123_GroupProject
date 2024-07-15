-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 16, 2024 at 12:47 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dad_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` int(20) NOT NULL,
  `room_id` varchar(20) NOT NULL,
  `room_type` varchar(20) NOT NULL,
  `total_price` decimal(20,0) NOT NULL,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `room_id`, `room_type`, `total_price`, `check_in_date`, `check_out_date`, `username`) VALUES
(4, 'K001', 'King', 210, '2024-07-15', '2024-07-16', 'lim'),
(5, 'S001', 'Single', 170, '2024-07-16', '2024-07-18', 'ajiq'),
(6, 'K003', 'King', 630, '2024-07-16', '2024-07-19', 'hisham'),
(7, 'S003', 'Single', 170, '2024-07-16', '2024-07-18', 'badrul'),
(8, 'K002', 'King', 420, '2024-07-16', '2024-07-18', 'ajiq'),
(9, 'S003', 'Single', 170, '2024-07-16', '2024-07-18', 'hisham'),
(10, 'S003', 'Single', 170, '2024-07-16', '2024-07-18', 'hisham'),
(11, 'K002', 'King', 420, '2024-07-16', '2024-07-18', 'lim'),
(12, 'D003', 'Double', 465, '2024-07-16', '2024-07-19', 'hisham'),
(13, 'S002', 'Single', 85, '2024-07-16', '2024-07-17', 'hisham');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(2000) NOT NULL,
  `email` varchar(20) NOT NULL,
  `phone_number` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone_number`) VALUES
(4, 'BADRUL', '$2y$10$pDufn7.HZnV2POwYBqXvY.S0zjD0DgePKCCFoyB2/DdAPXIzurmYK', 'B@gmail.com', '013245734'),
(5, 'hisham', '$2y$10$DAt3aKEiAUJ1PzPVd5B/nu6f6C9EAFKwppVD8DWWBqlBmqiwaYQPK', 'hisham@gmail.com', '0196357512'),
(6, 'badrul', '$2y$10$InEJ6oHzXDjZWf7VvBlGvedpnXoviUQW6flw9OwBFJRzgRgoks4M6', 'b@gmail.com', '019374365'),
(7, 'lim', '$2y$10$guqB3V5z80Cyj/1EgONIc.Bl7ONmVz49wsw0ekyBJiiQW0HyjCM5W', 'lim@gmail.com', '019378473'),
(8, 'ajiq', '$2y$10$2JD7ABiVXBQN3nlAzS1kduqnkrMxNYDkDFL3gwFZUZL8ce.gErcPC', 'ajiq@gmail.com', '018929374');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
