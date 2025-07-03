SUMMARY = "TeamViewer IoT Agent provides easy, fast and secure remote access to your device."
DESCRIPTION = "The TeamViewer IoT agent provides an out-of-the-box \
secure remote access and monitoring solution for your IoT environment \
and connected devices. . The agent seamlessly integrates with the \
latest TeamViewer client, which enables you to remotely access your \
device & remotely view live monitoring data. The Teamviewer Client can \
be downloaded at http://www.teamviewer.com/. . TeamViewer IoT Agent is \
currently available for free trial. As licensing may be subject to \
change, please visit https://teamviewer-iot.com. . Notes . Use of the \
TeamViewer IoT Agent must adhere to the end user license agreement. \
Reference http://www.teamviewer.com/link/?url=653670 . TeamViewer IoT \
Agent contains Free Software components. Reference \
/usr/share/doc/teamviewer-iot-agent/Third_Party_License.txt"
HOMEPAGE = "http://www.teamviewer.com"
SECTION = "non-free/misc"

LICENSE_FLAGS = "commercial"
LICENSE_FLAGS_DETAILS:${PN} = "For further details, see https://www.teamviewer.com/link/?url=418720" 

LIC_FILES_CHKSUM = "file://../copyright;md5=098680d7bb2ef92ca3cec0b546683658;\
	file://../Third_Party_License_IoT.txt;md5=d8029066642f39a3dcfa05a87651015c;"
	
SRC_URI += "file://copyright;md5=098680d7bb2ef92ca3cec0b546683658"
SRC_URI += "file://Third_Party_License_IoT.txt;md5=d8029066642f39a3dcfa05a87651015c"

LICENSE = "copyright & Third_Party_License_IoT.txt"

RDEPENDS:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'X11', ' xserver-xorg-xvfb xserver-xorg xkeyboard-config xauth', '', d)}"

RDEPENDS:${PN} += "bash perl glibc glibc-utils procps curl ca-certificates"
RRECOMMENDS:${PN} += "dbus libavahi-core libavahi-common libavahi-client"

TEAMVIEWER_IOT_AGENT_VERSION="3.5.10"

SRC_URI += "https://download-iot.teamviewer.com/agents/linux/3.5.10/amd64/teamviewer-iot-agent_3.5.10_amd64.deb"
SRC_URI[md5sum] = "aa84c2b07118cbceb723015aec1c8369"
SRC_URI[sha1sum] = "2b349d1ebe4adc786086b91b2eae37412eb6acdf"
SRC_URI[sha256sum] = "ed93ad340e119b7da4cc252a89502dbb4e71ae44625fa0a5ebc2caa7d5a87a63"
SRC_URI[sha384sum] = "4c15d8cb9efd5f7f400c85896924bfae1270fb01c90b9b984d3bd40107085606cf554807203cf81ea91def29e086b60e"
SRC_URI[sha512sum] = "862f9da2f51ee2149b1535d1db96f99c38025408dfb685f15d47cd53776bc8fac2fa804ee1264d2bdfe70d65bfd61529b0f76f2650a14289c3998f047f31add2"

# NOTE: no Makefile found, unable to determine what needs to be done

do_configure () {
	# Specify any needed configure commands here
	:
}

do_compile () {
	# Specify compilation commands here
	:
}

do_install () {
    #TeamViewer IoT Agent
	cp -r ${WORKDIR}/etc ${D}/etc
	cp -r ${WORKDIR}/lib ${D}/lib
	cp -r ${WORKDIR}/usr ${D}/usr
	cp -r ${WORKDIR}/var ${D}/var
	cp -r ${WORKDIR}/usr/share/doc ${D}/usr/share/teamviewer-iot-agent-layer-docs

	#Installation scripts
	ar x ${DL_DIR}/teamviewer-iot-agent_${TEAMVIEWER_IOT_AGENT_VERSION}_amd64.deb
	tar xf control.tar.gz --no-same-owner
	install -m 0700 preinst ${D}/usr/share/teamviewer-iot-agent/
	install -m 0700 postinst ${D}/usr/share/teamviewer-iot-agent/
    install -d ${D}/var/log/teamviewer-iot-agent/
    install -d ${D}/etc/default/
}

pkg_postinst_ontarget:${PN} () {
    mkdir -p /usr/share/doc /var/log/teamviewer-iot-agent
    mv /usr/share/teamviewer-iot-agent-layer-docs/* /usr/share/doc/
    /usr/share/teamviewer-iot-agent/preinst
    /usr/share/teamviewer-iot-agent/postinst
    rm -f /usr/share/teamviewer-iot-agent/preinst
    rm -f /usr/share/teamviewer-iot-agent-layer-docs
}

FILES:${PN} += "/etc/* \
		/var/* \
		/lib/* \
		/usr/*"

INSANE_SKIP:${PN} += "already-stripped ldflags"
