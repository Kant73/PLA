
abstract class Programme {

	public int selection;
	public int nbMaxAction;
	public String nom;
	/* ---- Ensemble d'actions */
	
	/* Gestion des actions */
	public void AjouterAction(Actions a){}
	
	public void DeplacerAction(Actions a){}
	
	public void SupprimerAction(Actions a){}
	
	public void InsererAction(Actions a){}
	
	/* Gestion des procedures */
	public void AjouterProcedure(Procedures a){}
	
	public void DeplacerProcedure(Procedures a){}
	
	public void SupprimerProcedure(Procedures a){}
	
	public void InsererProcedure(Procedures a){}
	
}
