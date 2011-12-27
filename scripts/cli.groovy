
public class cli {

	static main (args) {

		def om = options(args)
			if (om) {
				def gs = "Environment ${om.env}"
			if (om.cell) 
				gs += ", start at cell ${om.cell}"
			println gs
		}

	}

	static def options (def args) {
		def om = [:]
		def validEnvs = ['production','test','development']
		def shortEnvs = ['prod','test','dev']
		def cli = new CliBuilder()
		cli.with
			{
			h(longOpt: 'help', 'Help - Usage Information')
			e(longOpt: 'name', '(production | test | development)', args: 1, type: String, required: true)
			c(longOpt: 'cell', 'Start at cell', args: 1, type: Integer, optionalArg: true)
			}
		def opt = cli.parse(args)
		if (!opt) 
			return
		if (opt.h) 
			cli.usage()
		def given = opt.e
		if (!validEnvs.contains(opt.e)) 	{
			if (!shortEnvs.contains(opt.e)) 	{
				cli.usage()
				return
			}
			if (opt.e == 'dev')
				given = 'development'
			if (opt.e == 'prod')
				given = 'production'
		}
		println "${given}${opt.c}"
		om.put('env', given)
		om.put('cell', opt.c?:0)
		return om
	}

}