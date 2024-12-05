# Pour vocodeSimple()


# La longueur du nouveau tableau est égal à la longueur du premier multiplié par la fréquence

## Il y a 3 cas:
    si f == 1:
       f < 1:
       f > 1:

### Faire boucle allant de i = 0 à nouvelle taille du tableau quand freqScale est plus grand que 1 
    calculer l'indice de l'ancien tableau à partir de l'indice du nouveau tableau
    oldIndice = int(newIndice / frequence)
    newWave[i] = oldWave[oldindice]
    

# Voir schéma (Version minimale - idée générale sur : http://info.iut-bm.univ-fcomte.fr/staff/perrot/DUT-INFO/S1/SAE/2024/audio.html)


## Exemples :
    Si f = 0.7
    quand c'est inférieur à 1, on arrondie au supérieur, donc pour f = 0.7, on fait :
        (1 - 0.7) / 0.7 = 0.5
    
    
    quand c'est supérieur à 1, on arrondie à l'inférieur, donc pour f = 1.3, on fait :
        (1.3 - 1) / 1.3 il faudra faire l'inverse donc  3/ 13 = 13 / 3


    alors il faut ajouter un échantillon tous les 03
    (/ = échantillon à ajouter)
        ***/***/***/


