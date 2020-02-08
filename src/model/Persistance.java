package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Persistance {

	public static final String PROP_SEP = "##";
	public static final String SIZE_SEP = "@@";

	public static final String DATA_DIR = "data/";
	public static final String DATA = "data.dat";

	private static enum IDX {
		ID, NAME, AUTHOR, PRICE, SHAPE, SIZE, AREA
	}

	public static boolean save(PixelArt artwork) {

		String data = "";
		if (artwork.getId() != null) {
			System.err.println("Persistance save(): cannot persist object with id being not null.");
		}
		data += getNextId() + PROP_SEP;
		data += artwork.getName() + PROP_SEP;
		data += artwork.getAuthor() + PROP_SEP;
		data += artwork.getPrice() + PROP_SEP;

		Shape shape = artwork.getShape();
		data += shape.getShape() + PROP_SEP;
		switch (shape.getShape()) {
		case Shape.REC:
			data += shape.getWidth() + SIZE_SEP + shape.getHeight() + PROP_SEP;
			break;
		case Shape.SQR:
			data += shape.getWidth() + PROP_SEP;
			break;
		case Shape.CIR:
			data += shape.getRadius() + PROP_SEP;
			break;
		}

		data += shape.getArea() + "\n";
		try {
			byte[] dataBytes = data.getBytes("UTF-8");
			Path path = Paths.get(DATA_DIR + DATA);
			Files.write(path, dataBytes, StandardOpenOption.APPEND);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean update(PixelArt artwork) {

		Long id = artwork.getId();
		if (id == null) {
			System.err.println("Persistance update(): cannot update object with id being null.");
			return false;
		}
		try {
			List<String> lines = listLines();
			String linesStr = "";
			for (int i = 0; i != lines.size(); i++) {
				if (Long.valueOf(lines.get(i).split(PROP_SEP)[IDX.ID.ordinal()]) == id) {
					String data = "";
					data += artwork.getId() + PROP_SEP;
					data += artwork.getName() + PROP_SEP;
					data += artwork.getAuthor() + PROP_SEP;
					data += artwork.getPrice() + PROP_SEP;

					Shape shape = artwork.getShape();
					data += shape.getShape() + PROP_SEP;
					switch (shape.getShape()) {
					case Shape.REC:
						data += shape.getWidth() + SIZE_SEP + shape.getHeight() + PROP_SEP;
						break;
					case Shape.SQR:
						data += shape.getWidth() + PROP_SEP;
						break;
					case Shape.CIR:
						data += shape.getRadius() + PROP_SEP;
						break;
					}

					data += shape.getArea() + "\n";
					lines.set(i, data);
				}

				linesStr += lines.get(i) + "\n";
			}

			// override file with new data

			byte[] dataBytes = linesStr.getBytes("UTF-8");
			Path path = Paths.get(DATA_DIR + DATA);
			Files.write(path, dataBytes);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static List<PixelArt> list() {
		List<PixelArt> data = null;
		try {
			Path path = Paths.get(DATA_DIR + DATA);
			List<String> lines = Files.readAllLines(path);
			data = lines.stream().map(Persistance::parseLine).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return data.stream().filter( art -> art != null ).collect(Collectors.toList());
	}
	
	public static List<String> listLines(){
		Path path = Paths.get(DATA_DIR + DATA);
		List<String> lines;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
		return lines.stream().filter( Persistance::filterLines ).collect(Collectors.toList());
	}

	private static boolean filterLines(String line) {
		return line != null && line.split(PROP_SEP).length == IDX.values().length;
	}
	
	private static PixelArt parseLine(String line) {
		final PixelArt obj = new PixelArt();

		String[] properties = line.split(PROP_SEP);
		if (properties.length != IDX.values().length) {
			System.err.println("Parsing artwork data error. Line: " + line);
			return null;
		}
		obj.setId(Long.valueOf(properties[IDX.ID.ordinal()]));
		obj.setName(properties[IDX.NAME.ordinal()]);
		obj.setAuthor(properties[IDX.AUTHOR.ordinal()]);
		obj.setPrice(Double.valueOf(properties[IDX.PRICE.ordinal()]));
		switch (properties[IDX.SHAPE.ordinal()]) {
		case Shape.REC:
			String[] s = properties[IDX.SIZE.ordinal()].split(SIZE_SEP);
			obj.setShape(new Shape(Integer.valueOf(s[0]), Integer.valueOf(s[1])));
			break;
		case Shape.SQR:
			obj.setShape(new Shape(properties[IDX.SHAPE.ordinal()], Integer.valueOf(properties[IDX.SIZE.ordinal()])));
			break;
		case Shape.CIR:
			obj.setShape(new Shape(properties[IDX.SHAPE.ordinal()], Integer.valueOf(properties[IDX.SIZE.ordinal()])));
			break;
		default:
			System.err.println("Parsing shape error. Line: " + line);
			return null;
		}

		if (!Double.valueOf(properties[IDX.AREA.ordinal()]).equals(obj.getShape().getArea())) {
			System.err.println("Something went wrong with parsing shape, size or area." + "\nValue from line: "
					+ Double.valueOf(properties[IDX.AREA.ordinal()]) + "\nValue from object: "
					+ obj.getShape().getArea());
			return null;
		}

		return obj;
	}

	private static Long getNextId() {
		List<PixelArt> list = list();
		if (list.size() == 0)
			return 1l;
		return list.stream().map(artwork -> artwork.getId()).mapToLong(v -> v).max().orElse(1) + 1;
	}

}
