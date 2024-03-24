Set WshShell = WScript.CreateObject("WScript.Shell")
path = WScript.Arguments(0)
        WScript.Sleep 2000
        WshShell.SendKeys path
        WScript.Sleep 1000
        WshShell.SendKeys "{ENTER}"