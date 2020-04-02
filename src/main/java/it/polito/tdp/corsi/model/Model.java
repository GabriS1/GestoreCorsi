package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {

	private CorsoDAO dao;

	public Model() {
		dao = new CorsoDAO();
	}

	public List<Corso> getCorsiByPeriodo(Integer pd) {
		return dao.getCorsiByPeriodo(pd);
	}

	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd) {
		return dao.getIscrittiByPeriodo(pd);
	}

	public List<Studente> getStudentiByCorso(Corso c) {
		return dao.getStudentiByCorso(c);
	}

	public boolean esisteCorso(String s) {
		return dao.esisteCorso(s);
	}

	public Map<String, Integer> getDivisioneCDS(Corso c) {
		//SOLUZIONE 1
		
		/*List<Studente> studenti = dao.getStudentiByCorso(c);

		Map<String, Integer> statistiche = new HashMap<String, Integer>();

		for (Studente s : studenti) {
			if (s.getCds() != null && !s.getCds().equals("")) {
				if (statistiche.containsKey(s.getCds())) {
					statistiche.put(s.getCds(), statistiche.get(s.getCds()) + 1); // put rimpiazza il vaore associato
																					// alla key
				} else {
					statistiche.put(s.getCds(), 1);
				}
			}

		}

		return statistiche;*/
		
		
		//SOLUZIONE 2
		
		return dao.getDivisoneCDS(c);
		
	}
}
