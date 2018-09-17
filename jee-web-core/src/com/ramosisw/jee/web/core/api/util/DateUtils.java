package com.ramosisw.jee.web.core.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 
	 * @param localDateTime
	 *            fecha ser convertida a tipo Date
	 * @return fecha de tipo Date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Obtiene la diferencia en minutos de las fechas especificadas
	 * 
	 * @param startDate
	 *            Fecha inicial
	 * @param endDate
	 *            Fecha final (debe ser mayor a la inicial)
	 * @return Diferencia en minutos entra las dos fechas
	 */
	public static long getDateDifferenceInMinutes(Date startDate, Date endDate) {
		long diff = 0;
		if (startDate == null || endDate == null)
			return diff;
		diff = endDate.getTime() - startDate.getTime();
		return (diff / (60 * 1000));
	}

	/**
	 * Obtiene la diferencia en dias de las fechas especificadas
	 * 
	 * @param startDate
	 *            Fecha inicial
	 * @param endDate
	 *            Fecha final (debe ser mayor a la inicial)
	 * 
	 * @return Diferencia en minutos entra las dos fechas
	 */
	public static int getDateDiferenceInDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return 0;
		long diff = 0;
		diff = endDate.getTime() - startDate.getTime();

		return (int) (diff / (24 * 60 * 60 * 1000));
	}

	/**
	 * 
	 * @param date
	 *            fecha
	 * @param days
	 *            dias a agregar
	 * @return Date con los dias agregados
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
}
