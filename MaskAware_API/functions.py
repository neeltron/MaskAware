import sqlite3

def databaseentry(username,air,ir):
    con = sqlite3.connect('static/database.db')
    params = dict()
    try:
        with con:
                    cur = con.cursor()
                    cur.execute('INSERT OR IGNORE INTO maskaware (username, air,ir) VALUES ( ?, ?, ?)', (username,air,ir))
                    cur.execute('UPDATE maskaware SET air = ?, ir = ? WHERE username= ?', (air,ir,username))
                    con.commit()
        params['status'] = "success"       
    except:
        params['status'] = "fail"
    return params

def databaseread(username):
    con = sqlite3.connect('static/database.db')
    params = dict()
    try:
        with con:
                    cur = con.cursor()
                    cur.execute("Select * from maskaware where username = ? ",(username,))
                    rows = cur.fetchall()
                    for row in rows:
                        username = row[0]
                        air = row[1]
                        ir = row[2]
        params['status'] = "success"
        params['air']=air
        params['ir']=ir
    except:
        params['status'] = "fail"
    return params