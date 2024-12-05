# Pour vocodeSimple()


# La longueur du nouveau tableau est égal à la longueur du premier divisé par la fréquance
        Si la fréquence est < 1 : 
            taille += 1
        Sinon : 
            on ne change pas la taille



## Exemples :
    Si f = 0.7
    quand c'est inférieur à 1, on arrondie au supérieur, donc pour f = 0.7, on fait :
        (1 - 0.7) / 0.7 = 0.5
    
    
    quand c'est supérieur à 1, on arrondie à l'inférieur, donc pour f = 1.3, on fait :
        (1.3 - 1) / 1.3 il faudra faire l'inverse donc  3/ 13 = 13 / 3


    alors il faut ajouter un échantillon tous les 03
    (/ = échantillon à ajouter)
        ***/***/***/



si f = 0.2 (1 chance sur 5) alors choisir le début et la fin, et 