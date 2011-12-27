class MnRoadTagLib {
    static namespace = "mr"

    def signOutLink={attrs ->
        out << render(template:"/templates/common/signoutLinkTemplate")
    }

}


