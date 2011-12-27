package us.mn.state.dot.mnroad;

/**
 * Reads a large file without loading it all into memory
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Jan 25, 2010
 * Time: 12:10:31 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.*;
import java.io.*;

public class BigFile implements Iterable
{
    private BufferedReader _reader;

    public BigFile(String filePath) throws Exception
    {
	_reader = new BufferedReader(new FileReader(filePath));
    }

    public void Close()
    {
	try
	{
	    _reader.close();
	}
	catch (Exception ex) {}
    }

    public Iterator iterator()
    {
	return new FileIterator();
    }

    private class FileIterator implements Iterator
    {
	private String _currentLine;

	public boolean hasNext()
	{
	    try
	    {
		_currentLine = _reader.readLine();
	    }
	    catch (Exception ex)
	    {
		_currentLine = null;
		ex.printStackTrace();
	    }

	    return _currentLine != null;
	}

	public String next()
	{
	    return _currentLine;
	}

	public void remove()
	{
	}
    }
}