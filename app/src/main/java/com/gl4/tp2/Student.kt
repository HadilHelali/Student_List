class Student {
    var nom = "Ben Fouleni"
    var prenom : String = "Foulen"
    private var genre : String = "H"
    var etat : Boolean = false

    constructor(nom :String , prenom:String, genre:String){
        this.nom = nom
        this.prenom = prenom
        this.genre = genre
    }

    public fun getGender() : String{
        return this.genre
    }

    public fun ChangerEtat(checked : Boolean) {
        etat = checked
    }
}