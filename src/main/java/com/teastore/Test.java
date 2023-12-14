////images

package com.teastore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Stream;

public class Test {

	public static void main(String[] args) throws IOException {

        Path path = Paths.get("C:\\classified-app\\classified-backend\\uploads");

        String fileName="store-product-7.jpg";

        List<Path> paths = findByFileName(path, fileName);
        paths.forEach(x -> System.out.println(x));

    }

	 public static List<Path> findByFileName(Path path, String fileName)
		      throws IOException {

		      if (!Files.isDirectory(path)) {
		          throw new IllegalArgumentException("Path must be a directory!");
		      }

		      List<Path> result;
		      // walk file tree, no more recursive loop
		      try (Stream<Path> walk = Files.walk(path)) {
		          result = walk
		                  .filter(Files::isReadable)      // read permission
		                  .filter(Files::isRegularFile)   // is a file
		                  .filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName))
		                  .collect(Collectors.toList());
		      }
		      return result;

		  }
	
}
