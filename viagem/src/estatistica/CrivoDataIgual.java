package estatistica;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrivoDataIgual implements Crivo<Date> {

	private Date data;
	
	public CrivoDataIgual(Date e) {
		this.data = e;
		//System.out.println(new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(data));
		
	}

	@Override
	public boolean passa(Date objeto) {
		return data.equals(objeto);
	}

	public Date getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrivoDataIgual other = (CrivoDataIgual) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	
}
