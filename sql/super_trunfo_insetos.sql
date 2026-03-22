-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 22/03/2026 às 20:54
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `super_trunfo_insetos`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `carta`
--

CREATE TABLE `carta` (
  `id` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `grupo` char(2) NOT NULL,
  `super_trunfo` tinyint(1) NOT NULL DEFAULT 0,
  `comprimento_mm` decimal(7,2) NOT NULL,
  `peso_mg` decimal(10,3) NOT NULL,
  `velocidade_kmh` decimal(6,2) NOT NULL,
  `longevidade_dias` int(11) NOT NULL,
  `envergadura_mm` decimal(7,2) NOT NULL,
  `populacao_bilhoes` decimal(8,3) NOT NULL,
  `imagem_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Despejando dados para a tabela `carta`
--

INSERT INTO `carta` (`id`, `nome`, `grupo`, `super_trunfo`, `comprimento_mm`, `peso_mg`, `velocidade_kmh`, `longevidade_dias`, `envergadura_mm`, `populacao_bilhoes`, `imagem_path`) VALUES
(1, 'Abelha-europeia', '1A', 0, 15.00, 100.000, 30.00, 28, 32.00, 2000.000, 'images/Abelha-europeia.png'),
(2, 'Vespa-comum', '1B', 0, 18.00, 130.000, 32.00, 21, 35.00, 500.000, 'images/Vespa-comum.png'),
(3, 'Maribondo-caboclo', '1C', 0, 25.00, 300.000, 28.00, 30, 48.00, 100.000, 'images/Maribondo-caboclo.png'),
(4, 'Mamangava', '1D', 0, 28.00, 850.000, 22.00, 60, 55.00, 50.000, 'images/Mamangava.png'),
(5, 'Borboleta-monarca', '2A', 0, 95.00, 270.000, 32.00, 270, 100.00, 3.000, 'images/Borboleta-monarca.png'),
(6, 'Borboleta-azul-morpho', '2B', 0, 130.00, 400.000, 20.00, 100, 150.00, 0.500, 'images/Borboleta-azul-morpho.png'),
(7, 'Mariposa-atlas', '2C', 0, 120.00, 1200.000, 15.00, 10, 250.00, 0.200, 'images/Mariposa-atlas.png'),
(8, 'Borboleta-pavao', '2D', 0, 65.00, 180.000, 25.00, 14, 60.00, 1.000, 'images/Borboleta-pavao.png'),
(9, 'Formiga-cortadeira', '3A', 0, 12.00, 10.000, 2.00, 540, 0.00, 10000.000, 'images/Formiga-cortadeira.png'),
(10, 'Formiga-de-fogo', '3B', 0, 5.00, 2.000, 3.00, 180, 0.00, 5000.000, 'images/Formiga-de-fogo.png'),
(11, 'Formiga-bala', '3C', 0, 25.00, 90.000, 5.00, 365, 0.00, 100.000, 'images/Formiga-bala.png'),
(12, 'Formiga-legionaria', '3D', 0, 8.00, 5.000, 4.50, 720, 0.00, 8000.000, 'images/Formiga-legionaria.png'),
(13, 'Besouro-golias', '4A', 0, 110.00, 100000.000, 8.00, 90, 140.00, 0.050, 'images/Besouro-golias.png'),
(14, 'Besouro-hercules', '4B', 0, 170.00, 130000.000, 7.00, 120, 180.00, 0.030, 'images/Besouro-hercules.png'),
(15, 'Besouro-rinoceronte', '4C', 0, 45.00, 15000.000, 10.00, 90, 80.00, 2.000, 'images/Besouro-rinoceronte.png'),
(16, 'Vagalume-comum', '4D', 0, 15.00, 200.000, 10.00, 30, 28.00, 500.000, 'images/Vagalume-comum.png'),
(17, 'Gafanhoto-migratorio', '5A', 0, 60.00, 2000.000, 20.00, 56, 90.00, 1000.000, 'images/Gafanhoto-migratorio.png'),
(18, 'Grilo-domestico', '5B', 0, 25.00, 350.000, 5.00, 90, 40.00, 3000.000, 'images/Grilo-domestico.png'),
(19, 'Louva-a-deus-gigante', '5C', 0, 90.00, 5000.000, 12.00, 90, 120.00, 10.000, 'images/Louva-a-deus-gigante.png'),
(20, 'Bicho-pau', '5D', 0, 200.00, 20000.000, 3.00, 180, 220.00, 0.800, 'images/Bicho-pau.png'),
(21, 'Libelula-imperador', '6A', 0, 78.00, 1000.000, 56.00, 7, 96.00, 20.000, 'images/Libelula-imperador.png'),
(22, 'Libelula-azul', '6B', 0, 40.00, 300.000, 45.00, 7, 60.00, 50.000, 'images/Libelula-azul.png'),
(23, 'Mosquito-tigre', '6C', 0, 7.00, 2.500, 2.50, 30, 14.00, 80000.000, 'images/Mosquito-tigre.png'),
(24, 'Pernilongo-comum', '6D', 0, 6.50, 2.000, 2.00, 14, 12.00, 99999.999, 'images/Pernilongo-comum.png'),
(25, 'Barata-americana', '7A', 0, 40.00, 3500.000, 5.40, 365, 0.00, 99999.999, 'images/Barata-americana.png'),
(26, 'Barata-alema', '7B', 0, 16.00, 200.000, 4.80, 200, 0.00, 99999.999, 'images/Barata-alema.png'),
(27, 'Percevejo-de-cama', '7C', 0, 4.50, 1.500, 1.00, 300, 0.00, 1000.000, 'images/Percevejo-de-cama.png'),
(28, 'Barbeiro', '7D', 0, 25.00, 300.000, 40.00, 730, 50.00, 5.000, 'images/Barbeiro.png'),
(29, 'Pulga-comum', '8A', 0, 3.30, 0.450, 1.00, 100, 0.00, 99999.999, 'images/Pulga-comum.png'),
(30, 'Piolho-de-cabeca', '8B', 0, 3.00, 0.350, 0.00, 30, 0.00, 99999.999, 'images/Piolho-de-cabeca.png'),
(31, 'Carrapato-estrela', '8C', 0, 4.50, 2.000, 0.50, 365, 0.00, 2000.000, 'images/Carrapato-estrela.png'),
(32, 'Escorpiao-amarelo', '8D', 0, 70.00, 3000.000, 5.00, 730, 0.00, 3.000, 'images/Escorpiao-amarelo.png'),
(33, 'Abelha-gigante-do-Brasil', '9A', 1, 39.00, 8000.000, 35.00, 365, 80.00, 0.001, 'images/Abelha-gigante-do-Brasil.png');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `carta`
--
ALTER TABLE `carta`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `carta`
--
ALTER TABLE `carta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
