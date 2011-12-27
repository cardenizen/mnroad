import org.jsecurity.crypto.hash.Sha1Hash
import us.mn.state.dot.secure.JsecUser
import us.mn.state.dot.secure.JsecRole
import us.mn.state.dot.secure.JsecUserRoleRel
import us.mn.state.dot.secure.Ad

class LdapUserService {

    boolean transactional = true

    def grailsApplication
    def Long addUser(username, newRolesNames, roleIdxs, roleRelIdxs) {
      def jsecUserInstance = new JsecUser(username: username.trim(), passwordHash: new Sha1Hash("").toHex())
      if(!jsecUserInstance.hasErrors() && jsecUserInstance.save()) {
        def roles = []
        JsecRole aRole
        roleIdxs.each{ anNum ->
          try {
            def n = Integer.parseInt(anNum)
            if (n < newRolesNames.size()) {
              def roleName = newRolesNames[n].trim()
              aRole = JsecRole.findByName(roleName.trim())//getRole(roleName)
              if (aRole == null) {
                Boolean inAd = false
                Ad ad = new Ad()
                ad.configure(grailsApplication.config)
                def adRoles = ad.getUserRoles(username, true)
                inAd = adRoles.contains(roleName)
                aRole = new JsecRole(name: roleName, inDirectory: inAd).save()
              }
              roles.add(aRole)
            }
            else {
              log.info("Index '${n}' from roleIdxs is out of bounds.")
            }
          }
          catch (NumberFormatException nfe) {
            println "ohoh, NumberFormatException with: '${anNum}' in ${roleIdxs}"
          }
        }
        JsecUserRoleRel aRoleRel
        roleRelIdxs.each{ anNum ->
          try {
            def n = Integer.parseInt(anNum)
            if (n < newRolesNames.size()) {
              String rn = newRolesNames[n]
              def r = JsecRole.findByName(rn?.trim())//getRole(newRolesNames[n])
              aRoleRel = null
              if (r != null) {
                aRoleRel = new JsecUserRoleRel(user: jsecUserInstance, role: r).save()
              }
              if (aRoleRel != null) {
                log.info("Added user '${jsecUserInstance.username}' to role ${r.name}.")
              }
            }
            else {
              log.info("Index '${n}' on newRoleNames[${newRolesNames.size()}] is out of bounds.")
            }
          }
          catch (NumberFormatException nfe) {
            log.info("ohoh, NumberFormatException with: '${anNum}' accessing newRolesNames[${newRolesNames?.size()}]")
          }
        }

        return jsecUserInstance.id
      }
      else {
        return 0L
      }

    }

//    us.mn.state.dot.secure.JsecUser getUser(userName) {
//      us.mn.state.dot.secure.JsecUser.findByUsername(userName.trim())
//    }

//    us.mn.state.dot.secure.JsecRole getRole(roleName) {
//      us.mn.state.dot.secure.JsecRole.findByName(roleName.trim())
//    }
}
