package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateNme FROM country ORDER BY ccode";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int code = rs.getInt("ccode");
				String name = rs.getString("StateNme");
				
				if ( !idMap.containsKey(code) ) {
					Country country = new Country(code,name);
					idMap.put(code, country);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer,Country> idMap) {

        String sql = "SELECT state1no AS st1, state2no AS st2 FROM`contiguity` WHERE conttype = 1 AND"
        		+ " year <= ? AND state1no > state2no";
        List<Border> lista = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = idMap.get(rs.getInt("st1"));
				Country c2 = idMap.get(rs.getInt("st2"));
				Border border = new Border(c1,c2);
				lista.add(border);
			}
			
			conn.close();
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}
}
