// Usage:
// groovy dataset_to_sql.groovy <path to a csv file> <output file name>, e.g.:
// groovy dataset_to_sql.groovy dataset.csv output.sql
if (args.length != 2) {
    println 'Please provide a path to a csv file to import as well as output filename.'
    System.exit(-1)
}
def csvFile = new File(args[0])
if (!csvFile.canRead()) {
    println "Cannot read the file [${args[0]}]"
    System.exit(-1)
}

def outputFile = new File(args[1])
outputFile.withWriter('UTF-8') { writer ->
    csvFile.withReader('UTF-8') { reader ->
        def line, currentLine = 0
        while ((line = reader.readLine()) != null) {
            if (currentLine > 0) {
                def parts = line.split(',')
                writer.writeLine("call sw.insert_metrics('${parts[0]}', '${parts[1]}', '${parts[2]}', ${parts[3]}, ${parts[4]});")
            }
            currentLine++
        }
    }
}