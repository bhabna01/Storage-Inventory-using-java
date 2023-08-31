-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2020 at 12:04 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cat_ID` int(100) NOT NULL,
  `cat_Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`cat_ID`, `cat_Name`) VALUES
(1, 'Electrics'),
(3, 'Electronics'),
(4, 'Furnitures'),
(5, 'Toiletries');

-- --------------------------------------------------------

--
-- Table structure for table `cattest`
--

CREATE TABLE `cattest` (
  `ID` int(50) NOT NULL,
  `catName` varchar(100) NOT NULL,
  `catDetails` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `dv_ID` int(100) NOT NULL,
  `dv_PCode` varchar(100) NOT NULL,
  `dv_PName` varchar(100) NOT NULL,
  `dv_Qty` varchar(100) NOT NULL,
  `dv_Cat` varchar(100) NOT NULL,
  `dv_Sub` varchar(100) NOT NULL,
  `dv_Note` varchar(100) NOT NULL,
  `dv_Session` varchar(100) NOT NULL,
  `dv_Ref` varchar(100) NOT NULL,
  `dv_Date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`dv_ID`, `dv_PCode`, `dv_PName`, `dv_Qty`, `dv_Cat`, `dv_Sub`, `dv_Note`, `dv_Session`, `dv_Ref`, `dv_Date`) VALUES
(1, 'F001', 'Wooden Chairs', '2', 'Furnitures', 'Chair ', 'Na', 'Winter 2019', 'BL01', '01/02/2020'),
(2, 'EL001', 'LG Air Condition', '1', 'Electrics', 'Air-Condition', 'NA', 'Winter 2019', 'BL002', '03/03/2020'),
(4, 'EL001', 'LG Air Condition', '2', 'Electrics', 'Air-Condition', 'NA', 'Winter 2019', 'BL003', '06/03/2020'),
(5, 'EL001', 'LG Air Condition', '2', 'Electrics', 'Air-Condition', 'NA', 'Winter 2019', 'BL004', '07/03/2020');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `pro_ID` int(100) NOT NULL,
  `pro_Code` varchar(255) NOT NULL,
  `pro_Name` varchar(100) NOT NULL,
  `pro_Desc` varchar(255) NOT NULL,
  `sub_ID` varchar(100) NOT NULL,
  `cat_ID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pro_ID`, `pro_Code`, `pro_Name`, `pro_Desc`, `sub_ID`, `cat_ID`) VALUES
(2, 'F001', 'Wooden Chairs', 'Imported', 'Chair', 'Furnitures'),
(3, 'EL001', 'LG Air Condition', 'NA', 'Air-Condition', 'Electrics');

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `pur_ID` int(250) NOT NULL,
  `pro_Code` varchar(100) NOT NULL,
  `pro_Name` varchar(100) NOT NULL,
  `pur_Quantity` varchar(100) NOT NULL,
  `Unit_Cost` varchar(100) NOT NULL,
  `Total_Cost` varchar(100) NOT NULL,
  `pur_Desc` varchar(100) NOT NULL,
  `pur_Date` varchar(100) NOT NULL,
  `ven_ID` varchar(100) NOT NULL,
  `bill_No` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`pur_ID`, `pro_Code`, `pro_Name`, `pur_Quantity`, `Unit_Cost`, `Total_Cost`, `pur_Desc`, `pur_Date`, `ven_ID`, `bill_No`) VALUES
(1, 'EL001', 'LG Air Condition', '3', '45000', '135000', 'NA', '02/02/2020', 'Rashida Moni', 'BL001'),
(2, 'EL001', 'LG Air Condition', '6', '44000', '264000', 'Received Discount 1000 from each units', '03/03/2020 ', 'Shibu Pal', 'BL001'),
(4, 'F001', 'Wooden Chairs', '5', '1200', '6000', 'NA', '01/03/2020', 'Rashida Moni', 'BL004');

-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `ses_ID` int(100) NOT NULL,
  `ses_Name` varchar(255) NOT NULL,
  `ses_Details` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sessions`
--

INSERT INTO `sessions` (`ses_ID`, `ses_Name`, `ses_Details`) VALUES
(1, 'Winter 2019', 'End up by June 2023');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `ID` int(100) NOT NULL,
  `stk_PCode` varchar(100) NOT NULL,
  `stk_PName` varchar(100) NOT NULL,
  `stk_Quantity` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`ID`, `stk_PCode`, `stk_PName`, `stk_Quantity`) VALUES
(2, 'F001', 'Wooden Chairs', '2'),
(3, 'EL001', 'LG Air Condition', '4');

-- --------------------------------------------------------

--
-- Table structure for table `subcategory`
--

CREATE TABLE `subcategory` (
  `sub_ID` int(100) NOT NULL,
  `sub_Name` varchar(100) NOT NULL,
  `cat_ID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subcategory`
--

INSERT INTO `subcategory` (`sub_ID`, `sub_Name`, `cat_ID`) VALUES
(1, 'Air-Condition', 'Electrics'),
(3, 'Chair', 'Furnitures');

-- --------------------------------------------------------

--
-- Table structure for table `totalin`
--

CREATE TABLE `totalin` (
  `ID` int(100) NOT NULL,
  `ppCode` varchar(100) NOT NULL,
  `ppName` varchar(100) NOT NULL,
  `ppQty` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `totalin`
--

INSERT INTO `totalin` (`ID`, `ppCode`, `ppName`, `ppQty`) VALUES
(2, 'F001', 'Wooden Chairs', '10'),
(3, 'EL001', 'LG Air Condition', '7');

-- --------------------------------------------------------

--
-- Table structure for table `totalout`
--

CREATE TABLE `totalout` (
  `ID` int(100) NOT NULL,
  `dpCode` varchar(100) NOT NULL,
  `dpName` varchar(100) NOT NULL,
  `dpQty` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `totalout`
--

INSERT INTO `totalout` (`ID`, `dpCode`, `dpName`, `dpQty`) VALUES
(1, 'EL001', 'LG Air Condition', '5'),
(2, 'F001', 'Wooden Chairs', '3');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_ID` int(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `user_Name` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_ID`, `Name`, `user_Name`, `Password`, `Type`) VALUES
(2, 'Abier Farzana', 'abier', '12345', 'Admin'),
(3, 'Adeena Choudhury', 'adeena', '12345', 'Employee');

-- --------------------------------------------------------

--
-- Table structure for table `vendors`
--

CREATE TABLE `vendors` (
  `ven_ID` int(100) NOT NULL,
  `ven_Name` varchar(100) NOT NULL,
  `ven_Phone` varchar(20) NOT NULL,
  `ven_City` varchar(100) NOT NULL,
  `ven_Adress` varchar(255) NOT NULL,
  `ven_Email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vendors`
--

INSERT INTO `vendors` (`ven_ID`, `ven_Name`, `ven_Phone`, `ven_City`, `ven_Adress`, `ven_Email`) VALUES
(1, 'Shibu Pal', '0125865544', 'Dhaka', 'Fulbaria, Dhaka 1200', 'sh@gmail.com'),
(2, 'Rashida Moni', '014525554', 'Dhaka', 'ABC', 'r@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cat_ID`);

--
-- Indexes for table `cattest`
--
ALTER TABLE `cattest`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`dv_ID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`pro_ID`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`pur_ID`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`ses_ID`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `subcategory`
--
ALTER TABLE `subcategory`
  ADD PRIMARY KEY (`sub_ID`);

--
-- Indexes for table `totalin`
--
ALTER TABLE `totalin`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `totalout`
--
ALTER TABLE `totalout`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_ID`);

--
-- Indexes for table `vendors`
--
ALTER TABLE `vendors`
  ADD PRIMARY KEY (`ven_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `cat_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cattest`
--
ALTER TABLE `cattest`
  MODIFY `ID` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `delivery`
--
ALTER TABLE `delivery`
  MODIFY `dv_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `pro_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `pur_ID` int(250) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sessions`
--
ALTER TABLE `sessions`
  MODIFY `ses_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `subcategory`
--
ALTER TABLE `subcategory`
  MODIFY `sub_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `totalin`
--
ALTER TABLE `totalin`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `totalout`
--
ALTER TABLE `totalout`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `vendors`
--
ALTER TABLE `vendors`
  MODIFY `ven_ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
