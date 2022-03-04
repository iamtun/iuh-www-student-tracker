package iuh.fit.web.jdbc;

import java.nio.file.FileSystems;

public class TestPath {
	public static void main(String[] args) {
		String currentDirectoryPath = FileSystems.getDefault().
												  getPath("").
												  toAbsolutePath().
												  toString();
		System.out.println(currentDirectoryPath);// => F:\IUH.STUDY\K2N3_2022\www\workspace\student-tracker
	}
}
