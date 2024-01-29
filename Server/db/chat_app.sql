-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 13, 2024 lúc 11:42 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `chat_app`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `conversations`
--

CREATE TABLE `conversations` (
  `id` int(11) NOT NULL,
  `is_group` tinyint(4) DEFAULT 0,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `conversations`
--

INSERT INTO `conversations` (`id`, `is_group`, `created_at`, `updated_at`, `name`) VALUES
(1, 0, NULL, NULL, NULL),
(2, 0, NULL, NULL, NULL),
(3, 0, NULL, NULL, NULL),
(4, 1, '2024-01-10 00:04:13', '2024-01-10 00:04:13', 'Anh em'),
(23, 0, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `friend`
--

CREATE TABLE `friend` (
  `id` int(11) NOT NULL,
  `user_id_1` int(11) NOT NULL,
  `user_id_2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `friend`
--

INSERT INTO `friend` (`id`, `user_id_1`, `user_id_2`) VALUES
(26, 25, 24),
(28, 26, 24);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `messages`
--

CREATE TABLE `messages` (
  `id` int(11) NOT NULL,
  `conversation_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `content` mediumtext NOT NULL,
  `send_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_read` tinyint(4) DEFAULT 0,
  `attachment` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `messages`
--

INSERT INTO `messages` (`id`, `conversation_id`, `sender_id`, `receiver_id`, `content`, `send_time`, `is_read`, `attachment`, `type`) VALUES
(1, 1, 26, 24, 'aaaaaaaa', '2024-01-07 19:03:42', 0, NULL, 1),
(2, 1, 24, 26, 'aaaaaaaaaaaaaaa', '2024-01-07 19:03:45', 0, NULL, 1),
(3, 1, 26, 24, 'bbbbbbbbbbbb', '2024-01-07 19:03:46', 0, NULL, 1),
(4, 1, 24, 26, 'cccccccccccccccc', '2024-01-07 19:03:48', 0, NULL, 1),
(5, 1, 24, 26, 'aaaaa', '2024-01-07 19:03:50', 0, NULL, 1),
(6, 2, 25, 24, 'aaaaaaaaa', '2024-01-07 19:03:51', 0, NULL, 1),
(7, 2, 24, 25, 'oke', '2024-01-07 19:03:53', 0, NULL, 1),
(8, 3, 26, 25, 'oke', '2024-01-07 19:03:55', 0, NULL, 1),
(9, 1, 26, 24, 'oke', '2024-01-07 19:03:56', 0, NULL, 1),
(10, 1, 24, 26, 'aaaaaaaa', '2024-01-07 19:03:58', 0, NULL, 1),
(11, 2, 24, 25, 'aaaa', '2024-01-07 19:04:00', 0, NULL, 1),
(12, 2, 25, 24, 'aaaaaaaaaaaa', '2024-01-07 19:04:02', 0, NULL, 1),
(13, 2, 24, 25, 'aaaaaaaaaa', '2024-01-07 19:04:03', 0, NULL, 1),
(14, 1, 24, 26, 'aaaaaa', '2024-01-07 19:04:05', 0, NULL, 1),
(15, 1, 24, 26, 'aaaaaaaaaaaaa', '2024-01-07 19:04:07', 0, NULL, 1),
(16, 1, 24, 26, 'bbbb', '2024-01-07 19:04:09', 0, NULL, 1),
(17, 2, 24, 25, 'abc', '2024-01-07 19:04:11', 0, NULL, 1),
(18, 2, 25, 24, 'oke', '2024-01-07 19:04:12', 0, NULL, 1),
(19, 2, 24, 25, '2', '2024-01-07 19:17:22', 0, NULL, 2),
(20, 2, 25, 24, '8', '2024-01-07 19:17:32', 0, NULL, 2),
(21, 2, 24, 25, '3', '2024-01-07 19:46:25', 0, NULL, 2),
(22, 2, 24, 25, '7', '2024-01-07 19:54:10', 0, NULL, 2),
(23, 2, 24, 25, 'aaaa', '2024-01-07 19:54:20', 0, NULL, 1),
(59, 2, 24, 25, 'add friend', '2024-01-10 17:00:49', 0, NULL, 5),
(60, 2, 25, 24, 'Accept', '2024-01-10 17:00:49', 0, NULL, 1),
(61, 2, 24, 25, 'Đi chơi không', '2024-01-10 17:02:02', 0, NULL, 1),
(62, 2, 24, 25, '3', '2024-01-10 17:02:07', 0, NULL, 2),
(63, 2, 25, 24, 'oke', '2024-01-10 17:02:52', 0, NULL, 1),
(64, 2, 24, 25, 'Unfriend', '2024-01-10 17:27:12', 0, NULL, 1),
(65, 2, 24, 25, 'add friend', '2024-01-10 17:48:56', 0, NULL, 5),
(66, 2, 25, 24, 'Accept', '2024-01-10 17:48:56', 0, NULL, 1),
(67, 2, 24, 25, 'Unfriend', '2024-01-10 17:49:40', 0, NULL, 1),
(68, 2, 25, 24, 'add friend', '2024-01-10 17:50:15', 0, NULL, 5),
(69, 2, 24, 25, 'Accept', '2024-01-10 17:50:15', 0, NULL, 1),
(70, 2, 24, 25, 'Unfriend', '2024-01-10 17:55:50', 0, NULL, 1),
(71, 2, 25, 24, 'add friend', '2024-01-10 17:56:08', 0, NULL, 5),
(72, 2, 24, 25, 'Accept', '2024-01-10 17:56:08', 0, NULL, 1),
(73, 2, 24, 25, 'Unfriend', '2024-01-11 03:43:55', 0, NULL, 1),
(74, 2, 24, 25, 'add friend', '2024-01-11 03:44:07', 0, NULL, 5),
(75, 2, 25, 24, 'Accept', '2024-01-11 03:44:07', 0, NULL, 1),
(76, 23, 31, 24, 'add friend', '2024-01-11 04:19:29', 0, NULL, 5),
(77, 23, 24, 31, 'Accept', '2024-01-11 04:19:29', 0, NULL, 1),
(78, 23, 31, 24, 'Unfriend', '2024-01-11 04:19:42', 0, NULL, 1),
(79, 23, 24, 31, 'aaaaaaaaaaaaaaa', '2024-01-11 04:19:53', 0, NULL, 1),
(80, 23, 31, 24, 'aaaaaaaaaaaaaaa', '2024-01-11 04:20:19', 0, NULL, 1),
(81, 23, 24, 31, '3', '2024-01-11 04:20:24', 0, NULL, 2),
(82, 23, 24, 31, '6', '2024-01-11 04:20:26', 0, NULL, 2),
(83, 23, 24, 31, '8', '2024-01-11 04:20:26', 0, NULL, 2),
(84, 23, 24, 31, '8', '2024-01-11 04:20:27', 0, NULL, 2),
(85, 23, 31, 24, '3', '2024-01-11 04:20:33', 0, NULL, 2),
(86, 23, 31, 24, '3', '2024-01-11 04:20:33', 0, NULL, 2),
(87, 23, 31, 24, '3', '2024-01-11 04:20:33', 0, NULL, 2),
(88, 1, 24, 26, 'add friend', '2024-01-12 09:31:41', 0, NULL, 5),
(89, 1, 26, 24, 'Accept', '2024-01-12 09:31:41', 0, NULL, 1),
(90, 2, 24, 25, 'afsaasasas\\', '2024-01-12 19:36:15', 0, NULL, 1),
(91, 2, 25, 24, 'aaaaaaaaaa', '2024-01-12 19:36:43', 0, NULL, 1),
(92, 2, 24, 25, '3', '2024-01-12 19:36:52', 0, NULL, 2),
(93, 2, 24, 25, '3', '2024-01-12 19:36:52', 0, NULL, 2),
(94, 2, 24, 25, '3', '2024-01-12 19:36:52', 0, NULL, 2),
(95, 4, 24, 24, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(96, 4, 24, 25, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(97, 4, 24, 26, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(98, 4, 24, 27, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(99, 4, 24, 28, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(100, 4, 24, 29, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(101, 4, 24, 30, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(102, 4, 24, 31, 'aaaaaaa', '2024-01-12 21:38:51', 0, NULL, 1),
(103, 4, 24, 24, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(104, 4, 24, 25, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(105, 4, 24, 26, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(106, 4, 24, 27, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(107, 4, 24, 28, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(108, 4, 24, 29, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(109, 4, 24, 30, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(110, 4, 24, 31, 'aaaaaaaaa', '2024-01-12 21:43:36', 0, NULL, 1),
(111, 4, 25, 24, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(112, 4, 25, 25, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(113, 4, 25, 26, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(114, 4, 25, 27, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(115, 4, 25, 28, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(116, 4, 25, 29, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(117, 4, 25, 30, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(118, 4, 25, 31, 'aaaaaaa', '2024-01-12 21:58:24', 0, NULL, 1),
(119, 4, 24, 24, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(120, 4, 24, 25, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(121, 4, 24, 26, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(122, 4, 24, 27, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(123, 4, 24, 28, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(124, 4, 24, 29, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(125, 4, 24, 30, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(126, 4, 24, 31, 'aaaaaaaaa', '2024-01-13 07:18:56', 0, NULL, 1),
(127, 4, 24, 24, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(128, 4, 24, 25, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(129, 4, 24, 26, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(130, 4, 24, 27, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(131, 4, 24, 28, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(132, 4, 24, 29, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(133, 4, 24, 30, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(134, 4, 24, 31, '111111111', '2024-01-13 07:24:23', 0, NULL, 1),
(135, 4, 24, 24, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(136, 4, 24, 25, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(137, 4, 24, 26, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(138, 4, 24, 27, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(139, 4, 24, 28, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(140, 4, 24, 29, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(141, 4, 24, 30, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(142, 4, 24, 31, '1111111111', '2024-01-13 07:24:48', 0, NULL, 1),
(143, 4, 24, 24, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(144, 4, 24, 25, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(145, 4, 24, 26, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(146, 4, 24, 27, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(147, 4, 24, 28, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(148, 4, 24, 29, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(149, 4, 24, 30, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(150, 4, 24, 31, 'aaaaaaaa', '2024-01-13 07:28:16', 0, NULL, 1),
(151, 4, 24, 24, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(152, 4, 24, 25, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(153, 4, 24, 26, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(154, 4, 24, 27, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(155, 4, 24, 28, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(156, 4, 24, 29, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(157, 4, 24, 30, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(158, 4, 24, 31, 'hung', '2024-01-13 07:56:09', 0, NULL, 1),
(159, 4, 25, 24, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(160, 4, 25, 25, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(161, 4, 25, 26, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(162, 4, 25, 27, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(163, 4, 25, 28, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(164, 4, 25, 29, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(165, 4, 25, 30, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(166, 4, 25, 31, 'oke', '2024-01-13 07:56:31', 0, NULL, 1),
(167, 4, 25, 24, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(168, 4, 25, 25, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(169, 4, 25, 26, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(170, 4, 25, 27, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(171, 4, 25, 28, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(172, 4, 25, 29, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(173, 4, 25, 30, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(174, 4, 25, 31, 'aaaaaaaa', '2024-01-13 09:14:55', 0, NULL, 1),
(175, 4, 25, 24, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(176, 4, 25, 25, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(177, 4, 25, 26, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(178, 4, 25, 27, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(179, 4, 25, 28, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(180, 4, 25, 29, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(181, 4, 25, 30, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(182, 4, 25, 31, 'aaaa', '2024-01-13 09:14:59', 0, NULL, 1),
(183, 4, 25, 24, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(184, 4, 25, 25, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(185, 4, 25, 26, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(186, 4, 25, 27, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(187, 4, 25, 28, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(188, 4, 25, 29, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(189, 4, 25, 30, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(190, 4, 25, 31, '3', '2024-01-13 09:15:03', 0, NULL, 2),
(191, 4, 25, 24, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(192, 4, 25, 25, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(193, 4, 25, 26, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(194, 4, 25, 27, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(195, 4, 25, 28, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(196, 4, 25, 29, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(197, 4, 25, 30, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(198, 4, 25, 31, '5', '2024-01-13 09:15:07', 0, NULL, 2),
(199, 4, 25, 24, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(200, 4, 25, 25, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(201, 4, 25, 26, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(202, 4, 25, 27, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(203, 4, 25, 28, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(204, 4, 25, 29, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(205, 4, 25, 30, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(206, 4, 25, 31, '6', '2024-01-13 09:15:07', 0, NULL, 2),
(207, 4, 29, 24, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(208, 4, 29, 25, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(209, 4, 29, 26, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(210, 4, 29, 27, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(211, 4, 29, 28, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(212, 4, 29, 29, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(213, 4, 29, 30, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(214, 4, 29, 31, 'oke', '2024-01-13 10:28:36', 0, NULL, 1),
(215, 4, 24, 24, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(216, 4, 24, 25, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(217, 4, 24, 26, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(218, 4, 24, 27, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(219, 4, 24, 28, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(220, 4, 24, 29, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(221, 4, 24, 30, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(222, 4, 24, 31, 'ngon', '2024-01-13 10:28:56', 0, NULL, 1),
(223, 4, 25, 24, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(224, 4, 25, 25, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(225, 4, 25, 26, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(226, 4, 25, 27, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(227, 4, 25, 28, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(228, 4, 25, 29, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(229, 4, 25, 30, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(230, 4, 25, 31, '4', '2024-01-13 10:29:15', 0, NULL, 2),
(231, 4, 27, 24, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(232, 4, 27, 25, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(233, 4, 27, 26, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(234, 4, 27, 27, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(235, 4, 27, 28, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(236, 4, 27, 29, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(237, 4, 27, 30, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1),
(238, 4, 27, 31, 'đỉnh', '2024-01-13 10:29:26', 0, NULL, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_conversation`
--

CREATE TABLE `role_conversation` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role_conversation`
--

INSERT INTO `role_conversation` (`id`, `role_name`) VALUES
(1, 'user');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT '',
  `isOnline` tinyint(4) DEFAULT 1,
  `last_online` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `isOnline`, `last_online`) VALUES
(24, 'hung', '111', '111', '10', 1, '2024-01-13 10:36:11'),
(25, 'bach', '111', 'bach', '11', 1, '2024-01-13 10:31:22'),
(26, 'aaaa', '111', '111', '12', 1, '2024-01-12 21:43:54'),
(27, 'khanh', '111', '111', '13', 1, '2024-01-13 10:31:30'),
(28, 'Ngoc', '111', '111', '14', 1, '2024-01-11 13:05:37'),
(29, 'Hao', '111', '111', '15', 1, '2024-01-13 10:31:24'),
(30, 'Anh', '111', '111', '1', 1, '2024-01-11 13:05:37'),
(31, 'hung1111', '111', '111', '2', 1, '2024-01-11 13:05:37');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_conversation`
--

CREATE TABLE `user_conversation` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `conversation_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_conversation`
--

INSERT INTO `user_conversation` (`id`, `user_id`, `conversation_id`, `role_id`) VALUES
(1, 26, 1, 1),
(2, 24, 1, 1),
(3, 25, 2, 1),
(4, 24, 2, 1),
(5, 26, 3, 1),
(6, 25, 3, 1),
(7, 24, 4, 1),
(8, 25, 4, 1),
(9, 26, 4, 1),
(46, 31, 23, 1),
(47, 24, 23, 1),
(48, 27, 4, 1),
(49, 28, 4, 1),
(50, 29, 4, 1),
(51, 30, 4, 1),
(52, 31, 4, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `friend`
--
ALTER TABLE `friend`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id_1` (`user_id_1`),
  ADD KEY `user_id_2` (`user_id_2`);

--
-- Chỉ mục cho bảng `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `receiver_id` (`receiver_id`),
  ADD KEY `conversation_id` (`conversation_id`);

--
-- Chỉ mục cho bảng `role_conversation`
--
ALTER TABLE `role_conversation`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `user_conversation`
--
ALTER TABLE `user_conversation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `conversation_id` (`conversation_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `conversations`
--
ALTER TABLE `conversations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT cho bảng `friend`
--
ALTER TABLE `friend`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `messages`
--
ALTER TABLE `messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=239;

--
-- AUTO_INCREMENT cho bảng `role_conversation`
--
ALTER TABLE `role_conversation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT cho bảng `user_conversation`
--
ALTER TABLE `user_conversation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `friend`
--
ALTER TABLE `friend`
  ADD CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`user_id_1`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`user_id_2`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `messages_ibfk_3` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`);

--
-- Các ràng buộc cho bảng `user_conversation`
--
ALTER TABLE `user_conversation`
  ADD CONSTRAINT `user_conversation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_conversation_ibfk_2` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_conversation_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role_conversation` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
