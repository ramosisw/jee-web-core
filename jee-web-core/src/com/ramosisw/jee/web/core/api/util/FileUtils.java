package com.ramosisw.jee.web.core.api.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.ramosisw.jee.web.core.api.ex.BLException;
import com.ramosisw.jee.web.core.api.BaseErrorCodes;
import com.ramosisw.jee.web.core.api.to.ErrorType;
import com.ramosisw.jee.web.core.api.to.FilePropertiesType;

/**
 * Métodos de ayuda para el manejo de archivos
 * 
 * @author jcramos
 *
 */
public class FileUtils {

	public enum EChecksumType {
		MD5, SHA1, SHA256;
	}

	/**
	 * Reads the files path and returns its content as a byte array
	 * 
	 * @param file
	 *            File to be read
	 * 
	 * @return File content as a byte array or null if file is null or empty string
	 * 
	 * @throws IOException
	 *             en caso de no poder obtener el arreglo de bytes
	 */
	public static byte[] getFileBytes(String file) throws IOException {
		if (StringUtils.isEmpty(file))
			return null;

		return getFileBytes(new File(file));
	}

	/**
	 * Reads the files path and returns its content as a byte array
	 * 
	 * @param file
	 *            File to be read
	 * 
	 * @return File content as a byte array
	 * 
	 * @throws IOException
	 *             en caso de no poder obtener el arreglo de bytes
	 */
	public static byte[] getFileBytes(File file) throws IOException {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		byte[] buffer = new byte[4096];

		if (file == null || !file.canRead())
			return null;

		try {
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1)
				ous.write(buffer, 0, read);
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
				// swallow, since not that important
			}
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
				// swallow, since not that important
			}
		}
		return ous.toByteArray();
	}

	/**
	 * Reads specified file and returns its content as a Base64 UTF-8 String.
	 * 
	 * @param file
	 *            File to be read.
	 * 
	 * @return Content file as Base64 UTF-8 String or null if file is null, does not
	 *         exist or can be read
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public static String getFileBytesAsString(File file) throws IOException {
		if (file == null || !file.canRead())
			return null;

		return StringUtils.encodeBase64(getFileBytes(file));
	}

	/**
	 * Reads specified file and returns its content as a Base64 UTF-8 String.
	 * 
	 * @param filePath
	 *            Full file path to be read
	 * 
	 * @return Content file as Base64 UTF-8 String or null if String is empty or
	 *         null
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public static String getFileBytesAsString(String filePath) throws IOException {
		if (StringUtils.isEmpty(filePath))
			return null;

		return getFileBytesAsString(new File(filePath));
	}

	/**
	 * Obtiene una lista de archivos tipo imágenes jpg o png que se encuentren en la
	 * ruta especificada (incluye subdirectorios) y que cumplan con el criterio del
	 * nombre de archivo
	 * 
	 * @param dir
	 *            Ruta para buscar los archivos
	 * @param fileNamePreffix
	 *            Prefijo del nombre de archivo (e.g: 23455_)
	 * 
	 * @return Lista de imágenes encontradas en la ruta especificada
	 * @throws IOException
	 *             en caso de no poder listar las imagenes del directorio
	 * @see <a href=
	 *      "http://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/"
	 *      target=
	 *      "blank">http://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/</a>
	 * @see <a href="http://www.regexplanet.com/advanced/java/index.html" target=
	 *      "blank">http://www.regexplanet.com/advanced/java/index.html</a>
	 */
	public static List<File> listImagesFiles(String dir, String fileNamePreffix) throws IOException {
		if (StringUtils.isEmpty(dir))
			return new ArrayList<File>();

		if (StringUtils.isEmpty(fileNamePreffix))
			fileNamePreffix = "*";
		return listFiles(new File(dir).toPath(), fileNamePreffix.toLowerCase());
		// return listFiles(new File(dir).toPath(),
		// String.format("((?i)%s\\.(?i)(jpg|png|gif|bmp))$",
		// fileNamePreffix.toLowerCase()));
	}

	/**
	 * Obtiene una lista de archivos que cumplen con el criterio de búsqueda La
	 * busqueda se hace recursiva e incluye subdirectorios
	 * 
	 * @param dir
	 *            Ruta para buscar los archivos
	 * @param filePattern
	 *            Filtro para el nombre de archivo (e.g: 12335_*)
	 * @return Lista de los archivos encontrados
	 * @throws IOException
	 *             en caso de no poder procesar el directorio
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java"
	 *      target=
	 *      "blank">http://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java</a>
	 */
	public static List<File> listImages(Path dir, String filePattern) throws IOException {
		List<File> files = new ArrayList<File>();
		File file = null;

		if (dir == null)
			return files;

		if (filePattern == null || filePattern.trim().length() == 0)
			filePattern = "*.*";

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
				String.format("%s*.{png,jpg,bmp,jpeg,gif}", filePattern))) {
			for (Path path : stream) {
				file = path.toFile();
				if (file.isDirectory()) {
					files.addAll(listFiles(path, filePattern));
				} else {
					files.add(path.toFile());
				}
			}
		} catch (Exception e) {
			throw new IOException("Can't process " + dir.toAbsolutePath(), e);
		}

		return files;
	}

	public static List<File> listFiles(Path dir, String filePattern) throws IOException {
		List<File> files = new ArrayList<File>();
		File file = null;

		if (dir == null)
			return files;

		if (filePattern == null || filePattern.trim().length() == 0)
			filePattern = "*.*";

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filePattern)) {
			for (Path path : stream) {
				file = path.toFile();
				if (file.isDirectory()) {
					files.addAll(listFiles(path, filePattern));
				} else {
					files.add(path.toFile());
				}
			}
		} catch (Exception e) {
			throw new IOException("Can't process " + dir.toAbsolutePath(), e);
		}

		return files;
	}

	/**
	 * Obtiene la extensión del archivo especificado
	 * 
	 * @param file
	 *            Nombre de archivo (e.g: file.doc.gz)
	 * 
	 * @return Extensión del archivo (e.g: .gz)
	 */
	public static String getFileExtension(File file) {
		String extension = "";

		int idx = file.getName().lastIndexOf('.');
		if (idx >= 0) {
			extension = file.getName().substring(idx + 1);
		}

		return extension;
	}

	/**
	 * Calcula el checksum de un archivo usando el algoritmo MD5
	 * 
	 * @param file
	 *            Archivo para el que se calculara el checksum
	 * 
	 * @return String en hexadecimal con el checksum del archivo o null si el el
	 *         parámetro file es una referencia nula
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static String computeChecksum(File file) throws IOException {
		return computeChecksum(file, EChecksumType.MD5);
	}

	/**
	 * Calcula el checksum de un archivo usando el algoritmo especificado: MD5,
	 * SHA-1, SHA-256
	 * 
	 * @param file
	 *            Archivo para el que se calculara el checksum
	 * @param algorithm
	 *            Algoritmo hash a usar
	 * 
	 * @return String en hexadecimal con el checksum del archivo o null si el el
	 *         parámetro file es una referencia nula
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static String computeChecksum(File file, EChecksumType algorithm) throws IOException {
		byte[] bytes = null;
		byte[] hash = null;
		String checksum = null;

		if (file == null)
			return null;

		if (!file.canRead())
			throw new IOException("Can't have access to file:" + file.toString());

		try {
			bytes = Files.readAllBytes(file.toPath());
			hash = MessageDigest.getInstance(algorithm.name()).digest(bytes);
			checksum = DatatypeConverter.printHexBinary(hash);
		} catch (Exception e) {
			throw new IOException("Error while procesing file" + file.getAbsolutePath(), e);
		}

		return checksum;
	}

	/**
	 * Obtiene el checksum de la cadena de bytes usando el algoritmo MD5
	 * 
	 * @param bytes
	 *            arreglo del cual se quiere obtener el checksum
	 * 
	 * @return cadena del checksum
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static String computeChecksum(byte[] bytes) throws IOException {
		return computeChecksum(bytes, EChecksumType.MD5);
	}

	/**
	 * Obtiene el checksum de la cadena de bytes
	 * 
	 * @param bytes
	 *            arreglo del cual se quiere obtener el checksum
	 * @param algorithm
	 *            Algoritmo a utilizar para la obtencion del checksum
	 * @return cadena del checksum
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static String computeChecksum(byte[] bytes, EChecksumType algorithm) throws IOException {
		try {
			byte[] hash = MessageDigest.getInstance(algorithm.name()).digest(bytes);
			return DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("Error while procesing checksum", e);
		}
	}

	/**
	 * Comprueba si los datos son validos segun el checksum usando el algoritmo MD5
	 * 
	 * @param bytes
	 *            datos del fichero a comprobar
	 * @param checksum
	 *            checksum de comprobacion
	 * @return verdad si los checksum son identicos
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static boolean isValidChecksum(byte[] bytes, String checksum) throws IOException {
		return isValidChecksum(bytes, checksum, EChecksumType.MD5);
	}

	/**
	 * Comprueba si los datos son validos segun el checksum
	 * 
	 * @param bytes
	 *            datos del fichero a comprobar
	 * @param checksum
	 *            checksum de comprobacion
	 * @param algorithm
	 *            algoritmo usado en el <code>checksum</code>
	 * @return verdad si los checksum son identicos
	 * @throws IOException
	 *             al intentar computar el checksum del archivo
	 */
	public static boolean isValidChecksum(byte[] bytes, String checksum, EChecksumType algorithm) throws IOException {
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance(algorithm.name()).digest(bytes);
			String _checksum = DatatypeConverter.printHexBinary(hash);
			return _checksum.equalsIgnoreCase(checksum);
		} catch (Exception e) {
			throw new IOException("Error while procesing checksum", e);
		}
	}

	/**
	 * Obtiene la informacion de un fichero
	 * 
	 * @param path
	 *            ubicacion del fichero
	 * @return Propiedades del archivo en un objeto de tipo
	 *         <code>FilePropertiesType</code>
	 * @throws BLException
	 *             en caso de no encontrar el fichero su obtener informacion del
	 *             fichero
	 */
	public static FilePropertiesType getPropertiesBase64(Path path) throws BLException {

		FilePropertiesType fileproperties = new FilePropertiesType();
		FileInputStream fileInputStream = null;
		byte[] data;
		try {
			File f = new File(path.toString());

			if (!f.exists())
				throw new BLException(new ErrorType(BaseErrorCodes.BL_FILE_NOT_FOUND, "File not found"));

			data = new byte[(int) f.length()];
			fileproperties.setSize(BigInteger.valueOf(f.length()));
			fileproperties.setCheckSum(computeChecksum(f));
			fileproperties.setCompressionType(getFileExtension(f));
			fileproperties.setEncodingType("Base64");
			fileproperties.setFileName(f.getName());
			// convert file into array of bytes
			fileInputStream = new FileInputStream(f);
			fileInputStream.read(data);
			fileInputStream.close();

			fileproperties.setContent(StringUtils.encodeBase64(data));
		} catch (IOException ioe) {
			throw new BLException(new ErrorType(BaseErrorCodes.BL_IOEXCEPTION, ioe.getMessage()));
		}
		return fileproperties;
	}
}
